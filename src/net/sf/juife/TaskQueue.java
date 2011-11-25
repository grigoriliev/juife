/*
 *   juife - Java User Interface Framework Extensions
 *
 *   Copyright (C) 2005-2007 Grigor Iliev <grigor@grigoriliev.com>
 *
 *   This file is part of juife.
 *
 *   juife is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License version 2.1 as published by the Free Software Foundation.
 *
 *   juife is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with juife; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *   MA  02110-1301, USA
 */

package net.sf.juife;

import java.util.LinkedList;
import java.util.Vector;

import net.sf.juife.event.TaskQueueEvent;
import net.sf.juife.event.TaskQueueListener;

import static net.sf.juife.event.TaskQueueEvent.ID;


/**
 * The <code>TaskQueue</code> class represents a queue that holds tasks
 * which are executed in FIFO order.
 * Note that the tasks are removed from the queue before their execution.
 * @author Grigor Iliev
 */
public class TaskQueue {
	private final String name;
	private final LinkedList<Task> taskQueue = new LinkedList<Task>();
	private Task currentTask;
	
	private boolean started = false;
	private boolean cancel = false;
	private boolean stop = false;
	
	private boolean idle = true;
	
	/** Creates a new instance of <code>TaskQueue</code>. */
	public
	TaskQueue() { this("TaskQueue-" + getSerialNumber()); }
	
	/**
	 * Creates a new instance of <code>TaskQueue</code> with the specified name.
	 * @param name The name of this task queue.
	 */
	public
	TaskQueue(String name) { this(name, (Task[])null); }
	
	/**
	 * Creates a new instance of <code>TaskQueue</code> and adds the
	 * specified tasks to the queue.
	 * @param tasks The tasks to be added.
	 */
	public
	TaskQueue(Task... tasks) { this("TaskQueue-" + getSerialNumber(), tasks); }
	
	/**
	 * Creates a new instance of <code>TaskQueue</code> with the
	 * specified name and adds the specified tasks to the queue.
	 * @param name The name of this task queue.
	 * @param tasks The tasks to be added.
	 */
	public
	TaskQueue(String name, Task... tasks) {
		this.name = name;
		if(tasks == null) return;
		for(Task t : tasks) add(t);
	}
	
	/**
	 * Gets the name of this task queue.
	 * @return The name of this task queue.
	 */
	public String
	getName() { return name; }
	
	/**
	 * Adds the specified task to the queue.
	 * Note that once the queue is started this method throws
	 * an exception if the queue is not running.
	 * @param task The task to be added.
	 * @throws IllegalStateException If the queue is not running.
	 */
	public synchronized void
	add(Task task) {
		boolean b;
		
		if(isStopped()) throw new IllegalStateException(getName() + " queue is stopped");
		
		taskQueue.add(task);
		
		if(taskQueue.size() == 1)
			fireTaskQueueEvent(new TaskQueueEvent(this, ID.FILLED));
		
		if(isIdle()) {
			idle = false;
			fireTaskQueueEvent(new TaskQueueEvent(this, ID.NOT_IDLE));
		}
		
		notifyAll();
	}
	
	/**
	 * Starts the processing of tasks in the queue.
	 * @throws IllegalStateException If the queue is already running.
	 */
	public synchronized void
	start() {
		if(isRunning())
			throw new IllegalStateException(getName() + " queue is already running");
		
		if(isStarted()) {
			if(!isStopped()) 
			
			stop = false;
			cancel = false;
		}
		
		started = true;
		fireTaskQueueEvent(new TaskQueueEvent(this, ID.STARTED));
		
		new Thread(name) {
			public void
			run() { start0(); }
		}.start();
	}
	
	private void
	start0() {
		for(;;) {
			processTheQueue();
			
			if(isStopped()) {
				if(!isCancelled() && !isEmpty()) processTheQueue();
				break;
			}
			
			synchronized(this) {
				if(!isEmpty()) continue;
				try { wait(); }
				catch(Exception x) { x.printStackTrace(); }
			}
		}
	}
	
	private void
	processTheQueue() {
		if(isEmpty()) return;
		
		while(!isCancelled()) {
			synchronized(this) {
				currentTask = taskQueue.poll();
				fireTaskQueueEvent (
					new TaskQueueEvent(currentTask, ID.TASK_FETCHED)
				);
				if(isEmpty()) fireTaskQueueEvent (
					new TaskQueueEvent(this, ID.EMPTY)
				);
			}
			currentTask.invokeAndWait();
			synchronized(this) {
				fireTaskQueueEvent (
					new TaskQueueEvent(currentTask, ID.TASK_DONE)
				);
				
				currentTask = null;
				
				if(isEmpty()) {
					idle = true;
					fireTaskQueueEvent (
						new TaskQueueEvent(this, ID.IDLE)
					);
					return;
				}
			}
		}
	}
	
	/**
	 * Determines whether this task queue has been started.
	 * Note that once the queue is started this method returns <code>true</code>
	 * even if the queue is not running.
	 * @return <code>true</code> if the task queue has been started,
	 * <code>false</code> otherwise.
	 * @see #isRunning
	 * @see #isStopped
	 */
	public synchronized boolean
	isStarted() { return started; }
	
	/**
	 * Stops the task queue and cancels the execution of all pending tasks.
	 * Note that this method does not remove the pending tasks from the queue.
	 * @see #removePendingTasks
	 * @see #stop
	 */
	public synchronized void
	cancel() {
		cancel = true;
		stop();
	}
	
	/**
	 * Determines whether the task queue is cancelled.
	 * @return <code>true</code> if the task queue is cancelled, <code>false</code> otherwise.
	 */
	public synchronized boolean
	isCancelled() { return cancel; }
	
	/**
	 * Stops the task queue.
	 * Note that the already queued tasks will be processed. Use {@link #isIdle}
	 * method to determine whether all tasks in the queue are processed.
	 * @see #cancel
	 */
	public synchronized void
	stop() {
		stop = true;
		fireTaskQueueEvent(new TaskQueueEvent(this, ID.STOPPED));
		notifyAll();
	}
	
	/**
	 * Determines whether this task queue is stopped.
	 * @return <code>true</code> if the task queue is stopped, <code>false</code> otherwise.
	 */
	public synchronized boolean
	isStopped() { return stop; }
	
	/**
	 * Determines whether this task queue is running.
	 * The task queue is running if it has been started and is not stopped at this moment.
	 * @return <code>true</code> if the task queue is running, <code>false</code> otherwise.
	 */
	public synchronized boolean
	isRunning() { return isStarted() && !isStopped(); }
	
	/**
	 * Determines whether the task queue is empty.
	 * Note that the tasks are removed from the queue before their execution.
	 * This means that we still may have a running task even if the queue is empty.
	 * In other words,
	 * if the queue is empty doesn't mean that there is no running task at that moment.
	 * @return <code>true</code> if the queue is empty, <code>false</code> otherwise.
	 * @see #isIdle
	 * @see #getRunningTask
	 */
	public synchronized boolean
	isEmpty() { return taskQueue.isEmpty(); }
	
	/**
	 * Determines whether the queue is in idle state. Idle state means that the queue
	 * is empty and there is no running task.
	 * @return <code>true</code> if the queue is in idle state, <code>false</code> otherwise.
	 */
	public synchronized boolean
	isIdle() { return idle; }
	
	/**
	 * Gets the number of pending tasks.
	 * @return The number of pending tasks.
	 */
	public synchronized int
	getPendingTaskCount() { return taskQueue.size(); }
	
	/**
	 * Gets a list of all tasks in the queue pending for execution.
	 * @return An array containing all tasks pending for execution.
	 */
	public synchronized Task[]
	getPendingTasks() { return taskQueue.toArray(new Task[taskQueue.size()]); }
	
	/**
	 * Gets the currently running task.
	 * @return The task that is currently running or <code>null</code>
	 * if there is no running task at this moment.
	 */
	public synchronized Task
	getRunningTask() { return currentTask; }
	
	/** Removes all pending tasks. */
	public synchronized void
	removePendingTasks() { taskQueue.clear(); }
	
	/**
	 * Removes the specified task from the queue.
	 * @param t The task to be removed.
	 * @return <code>true</code> if the queue contains the specified element,
	 * <code>false</code> otherwise.
	 */
	public synchronized boolean
	removeTask(Task t) { return taskQueue.remove(t); }
	
	
	private static int serial = 0;
	
	private static synchronized int
	getSerialNumber() { return ++serial; }
	
	private final Vector<TaskQueueListener> listenerList = new Vector<TaskQueueListener>();
	
	/**
	 * Registers the specified listener for receiving event messages.
	 * @param l The <code>TaskListener</code> to register.
	 */
	public void
	addTaskQueueListener(TaskQueueListener l) { listenerList.add(l); }
	
	/**
	 * Removes the specified listener.
	 * @param l The <code>TaskQueueListener</code> to remove.
	 */
	public void
	removeTaskQueueListener(TaskQueueListener l) { listenerList.remove(l); }
	
	private synchronized void
	fireTaskQueueEvent(final TaskQueueEvent e) {
		PDUtils.runOnUiThread(new Runnable() {
			public void
			run() { for(TaskQueueListener l : listenerList) l.stateChanged(e); }
		});
	}
}
