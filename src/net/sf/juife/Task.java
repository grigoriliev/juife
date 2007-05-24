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

import net.sf.juife.event.TaskListener;


/**
 * Defines a task that can be queued for execution in a
 * <code>TaskQueue</code> or in a <code>TaskList</code>.
 * @see TaskQueue
 * @see TaskList
 * @author  Grigor Iliev
 */
public interface Task<R> {
	/**
	 * Registers the specified listener for receiving event messages.
	 * @param l The <code>TaskListener</code> to register.
	 */
	public void addTaskListener(TaskListener l);
	
	/**
	 * Removes the specified listener.
	 * @param l The <code>TaskListener</code> to remove.
	 */
	public void removeTaskListener(TaskListener l);
	
	/**
	 * Determines whether the task has finished execution.
	 * @return <code>true</code> if the task has finished execution,
	 * <code>false</code> otherwise.
	 */
	public boolean done();
	
	/**
	 * Determines whether the task has finished its execution successfully.
	 * @return <code>true</code> if the task has <b>not</b> finished its
	 * execution successfully; <code>false</code> otherwise
	 */
	public boolean doneWithErrors();
	
	/**
	 * Gets an appropriate error code identifying the failure of the task.
	 * @return An appropriate error code identifying the error.
	 * @see #doneWithErrors
	 */
	public int getErrorCode();
	
	/**
	 * Gets an appropriate error message when the task fails.
	 * @return An appropriate error message describing the failure of the task.
	 * @see #doneWithErrors
	 */
	public String getErrorMessage();
	
	/** Gets a a detailed error information. */
	public String getErrorDetails();
	
	/**
	 * Gets the result of the task execution.
	 * @return <code>R</code> instance providing the result of the task execution.
	 */
	public R getResult();
	
	/**
	 * Gets a short description about this task.
	 * @return An arbitrary text describing this task.
	 */
	public String getDescription();
	
	/**
	 * Starts the execution of this task. This method returns immediately after the
	 * the task is started. If you want to wait until the end of execution consider using
	 * {@link #invokeAndWait}. Notice that every task can be started only once.
	 * @throws IllegalStateException if the task has been started already.
	 * @see #invokeAndWait
	 */
	public void invoke();
	
	/**
	 * Starts the execution of this task.
	 * This method blocks until the task finishes its execution. Consider using {@link #invoke}
	 * if you don't want this method to wait until the end of the task execution.
	 * Notice that every task can be started only once.
	 * @throws IllegalStateException if the task has been started already.
	 * @see #invoke
	 */
	public void invokeAndWait();
	
	/**
	 * Determines whether the task has been started.
	 * @return <code>true</code> if the task is already started and <code>false</code>
	 * if the task is not started yet.
	 */
	public boolean isStarted();
	
	/**
	 * Gets the title of this task.
	 * @return The title of this task.
	 */
	public String getTitle();
	
	/**
	 * Sets the title of this task.
	 * @param title Specifies the title text of this task.
	 */
	public void setTitle(String title);
	
	/** Terminates the execution of this task */
	public void stop();
}
