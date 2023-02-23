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

import java.util.ArrayList;
import java.util.Vector;

import net.sf.juife.event.GenericEvent;
import net.sf.juife.event.GenericListener;


/**
 * This class represents a list of tasks held for further processing.
 * @author  Grigor Iliev
 */
public class TaskList {
	private final String name;
	private final Vector<Task> taskList = new Vector<Task>();
	
	private boolean started = false;
	private boolean processed = false;
	
	/**
	 * Creates a new instance of <code>TaskList</code>.
	 */
	public
	TaskList() { this("TaskList-" + getSerialNumber()); }
	
	/**
	 * Creates a new instance of <code>TaskList</code> with the specified name.
	 * @param name The name of the task list.
	 */
	public
	TaskList(String name) { this(name, (Task[])null); }
	
	/**
	 * Creates a new instance of <code>TaskList</code> and adds the specified tasks.
	 * @param tasks The tasks to be added to the newly created task list.
	 */
	public
	TaskList(Task... tasks) { this("TaskList-" + getSerialNumber(), tasks); }
	
	/**
	 * Creates a new instance of <code>TaskList</code> with
	 * the specified name and adds the specified tasks to the list.
	 * @param name The name of the task list.
	 * @param tasks The tasks to be added to the newly created task list.
	 */
	public
	TaskList(String name, Task... tasks) {
		this.name = name;
		if(tasks == null) return;
		for(Task t : tasks) add(t);
	}
	
	/**
	 * Adds the specified task to this task list.
	 * @param task The task to be added.
	 * @throws IllegalStateException If the processing
	 * of the tasks in the list is already started.
	 */
	public void
	add(Task task) {
		if(isStarted())
			throw new IllegalStateException("The TaskList is already started");
		taskList.add(task);
	}
	
	/** Starts the processing of the tasks in this list. */
	public void
	process() {
		if(started) throw new IllegalStateException("Already started");
		started = true;
		new Thread(name) {
			public void
			run() {
				for(Task t : taskList) { t.invokeAndWait();  }
				fireActionPerformed();
				processed = true;
			}
		}.start();
	}
	
	/**
	 * Determines whether the processing of the tasks in the list has been started.
	 * @return <code>true</code> if the processing of the tasks in
	 * the list has been started, <code>false</code> otherwise.
	 */
	public boolean
	isStarted() { return started; }
	
	/**
	 * Determines whether all of the tasks in the list are done.
	 * @return <code>true</code> if all tasks in the list are done,
	 * <code>false</code> otherwise.
	 */
	public boolean
	isProcessed() { return processed; }
	
	private static int serial = 0;
	
	private static synchronized int
	getSerialNumber() { return ++serial; }
	
	/** A list of event listeners for this <code>TaskList</code>. */
	protected final ArrayList<GenericListener> listenerList = new ArrayList<GenericListener>();
	
	/**
	 * Registers the specified <code>GenericListener</code> to be
	 * notified when all tasks in the list are done.
	 * @param l The <code>GenericListener</code> to register.
	 */
	public void
	addListener(GenericListener l) { listenerList.add(l); }
	
	/**
	 * Removes the specified listener.
	 * @param l The <code>GenericListener</code> to remove.
	 */
	public void
	removeActionListener(GenericListener l) { listenerList.remove(l); }
	
	/** Notifies registered listeners that the history list has changed. */
	private void
	fireActionPerformed() {
		try {
			PDUtils.runOnUiThreadAndWait(new Runnable() {
				public void
				run() { fireActionPerformed0(); }
			});
		} catch(Exception x) { x.printStackTrace(); }
	}
	
	/**
	 * Notifies registered listeners that the history list has changed.
	 * This method should be invoked from the event-dispatching thread.
	 */
	private void
	fireActionPerformed0() {
		GenericEvent e = new GenericEvent(this);
		for(int i = listenerList.size() - 1; i >= 0; i--) {
			listenerList.get(i).jobDone(e);
		}
	}
}
