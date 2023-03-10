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

package com.grigoriliev.jsampler.juife.event;

import com.grigoriliev.jsampler.juife.TaskQueue;

/**
 * A semantic event which indicates that the state of a task queue is changed.
 * @author Grigor Iliev
 */
public class TaskQueueEvent extends java.util.EventObject {
	/** Represents the <code>TaskQueueEvent</code> type. */
	public enum ID {
		/** Indicates that the task queue is started. */
		STARTED,
		
		/** Indicates that the task queue is stopped. */
		STOPPED,
		
		/** Indicates that the task queue is empty. */
		EMPTY,
		
		/** Indicates that the task queue is in idle state. */
		IDLE,
		
		/** Indicates that the task queue has just exited the idle state. */
		NOT_IDLE,
		
		/** Indicates that the task queue has changed the state from empty to filled. */
		FILLED,
		
		/**
		 * Indicates that a new task has been fetched for execution from the queue.
		 * @see TaskQueue#getRunningTask
		 */
		TASK_FETCHED,
		
		/**
		 * Indicates that the current running task is done.
		 * @see TaskQueue#getRunningTask
		 */
		TASK_DONE
	}
	
	private final ID eventID;
	
	/**
	 * Creates a new instance of <code>TaskQueueEvent</code>.
	 * @param source The object that originated the event.
	 * @param eventID The event ID.
	 */
	public TaskQueueEvent(Object source, ID eventID) {
		super(source);
		this.eventID = eventID;
	}
	
	/**
	 * Identifies the event type.
	 * @return The event ID.
	 */
	public ID
	getEventID() { return eventID; }
}
