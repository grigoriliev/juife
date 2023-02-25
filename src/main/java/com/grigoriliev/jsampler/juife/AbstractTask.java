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

package com.grigoriliev.jsampler.juife;

import java.util.ArrayList;

import com.grigoriliev.jsampler.juife.event.TaskEvent;
import com.grigoriliev.jsampler.juife.event.TaskListener;

/**
 * Provides default implementation of the <code>Task</code> interface.
 * This class can be used to facilitate the monitoring of time-consuming task.
 *
 * Note that all event notifications are done on the event dispatching thread. This means
 * that the event handlers can safely perform operations on Swing components.
 */
public abstract class AbstractTask<R> implements Task<R>, Runnable {
	public static int UNKNOWN_ERROR = -1;
	
	private int errorCode = UNKNOWN_ERROR;
	private String errorMessage = null;
	private String errorDetails = null;
	private boolean isDone = false;
	private boolean started = false;
	private boolean doneWithErrors = false;
	private String title = "com.grigoriliev.jsampler.juife.AbstractTask";
	private String desc = null;
	
	private R result = null;
	
		
	/**
	 * Determines whether the task has finished execution.
	 * @return <code>true</code> if the task has finished execution,
	 * <code>false</code> otherwise.
	 */
	public boolean
	done() { return isDone; }
	
	/**
	 * Sets whether the task has finished execution.
	 * @param b Specify <code>true</code> to indicate that the task has
	 * finished execution and <code>false</code> otherwise.
	 */
	public void
	setDone(boolean b) { isDone = true; }
	
	/**
	 * Determines whether the task has finished its execution successfully.
	 * @return <code>true</code> if the task has <b>not</b> finished its
	 * execution successfully; <code>false</code> otherwise
	 */
	public boolean
	doneWithErrors() { return doneWithErrors; }
	
	/**
	 * Sets whether the execution of the task fails.
	 * @param b Specify <code>true</code> to indicate that the execution of the task fails.
	 */
	public void
	setDoneWithErrors(boolean b) { doneWithErrors = b; }
	
	/**
	 * Gets an appropriate error code identifying the failure of the task.
	 * @return An appropriate error code identifying the error.
	 * @see #doneWithErrors
	 */
	public int
	getErrorCode() { return errorCode; }
	
	/**
	 * Sets an error code identifying the failure of the task.
	 * @param code Specifies the error code identifying the failure of the task.
	 */
	public void
	setErrorCode(int code) { errorCode = code; }
		
	/**
	 * Gets an appropriate error message when the task fails.
	 * @return An appropriate error message describing the failure of the task.
	 * @see #doneWithErrors
	 */
	public String
	getErrorMessage() { return errorMessage; }
	
	/**
	 * Set the error message of this task.
	 * @param msg Specifies the error message describing the failure of the task.
	 */
	public void
	setErrorMessage(String msg) {
		errorMessage = msg;
		setDoneWithErrors(true);
	}
	
	/** Gets a detailed error information. */
	public String
	getErrorDetails() { return errorDetails; }
	
	/** Sets a a detailed error information. */
	public void
	setErrorDetails(String details) { errorDetails = details; }
	
	/**
	 * Gets the title of this task.
	 * @return The title of this task.
	 */
	public String
	getTitle() { return title; }
	
	/**
	 * Sets the title of this task.
	 * @param title Specifies the title text of this task.
	 */
	public void
	setTitle(String title) { this.title = title; }
	
	/**
	 * Gets a short description about this task.
	 * @return An arbitrary text describing this task.
	 */
	public String
	getDescription() { return desc; }
	
	/**
	 * Sets the description of this task.
	 * @param desc An arbitrary text describing the task.
	 */
	public void
	setDescription(String desc) { this.desc = desc; }
	
	/**
	 * Starts the execution of this task. This method returns immediately after the
	 * the task is started. If you want to wait until the end of execution consider using
	 * {@link #invokeAndWait}. Notice that every task can be started only once.
	 * @throws IllegalStateException if the task has been started already.
	 * @see #invokeAndWait
	 */
	public void
	invoke() {
		new Thread(getTitle()) {
			public void
			run() { invokeAndWait(); }
		}.start();
	}
	
	/**
	 * Starts the execution of this task.
	 * This method blocks until the task finishes its execution. Consider using {@link #invoke}
	 * if you don't want this method to wait until the end of the task execution.
	 * Notice that every task can be started only once.
	 * @throws IllegalStateException if the task has been started already.
	 * @see #invoke
	 */
	public void
	invokeAndWait() {
		if(isStarted()) throw new IllegalStateException("Task already started");
		started = true;
		run();
		setDone(true);
		
		try { PDUtils.runOnUiThreadAndWait(new Runnable() {
			public void
			run() { fireTaskPerformed(); }
		});}
		catch(Exception x) { x.printStackTrace(); }
	}
	///////
	
	/**
	 * Determines whether the task has been started.
	 * @return <code>true</code> if the task is already started and <code>false</code>
	 * if the task is not started yet.
	 */
	public boolean
	isStarted() { return started; }
	
	/**
	 * Gets the result of the task execution.
	 * @return <code>R</code> instance providing the result of the task execution.
	 */
	public R
	getResult() { return result; }
	
	/**
	 * Sets the result of the task execution.
	 * @param result <code>R</code> instance providing the result of the task execution.
	 */
	public void
	setResult(R result) { this.result = result; }
	
	///////
	private final ArrayList<TaskListener> listenerList = new ArrayList<TaskListener>();
	private TaskEvent taskEvent = null;
	
	/**
	 * Registers the specified <code>TaskListener</code> to be
	 * notified when task is done.
	 * @param l The <code>TaskListener</code> to register.
	 */
	public void
	addTaskListener(TaskListener l) { listenerList.add(l); }
	
	/**
	 * Removes the specified listener.
	 * @param l The <code>TaskListener</code> to remove.
	 */
	public void
	removeTaskListener(TaskListener l) { listenerList.remove(l); }
	
	/**
	 * Notifies listeners that the task has been done.
	 * This method should be invoked from the event-dispatching thread.
	 */
	private void
	fireTaskPerformed() {
		for(int i = listenerList.size() - 1; i >= 0; i--) {
			if(taskEvent == null) taskEvent = new TaskEvent(this);
			listenerList.get(i).taskPerformed(taskEvent);
		}
	}
}
