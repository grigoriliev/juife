/*
 *   juife - Java User Interface Framework Extensions
 *
 *   Copyright (C) 2011 Grigor Iliev <grigor@grigoriliev.com>
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

package com.grigoriliev.jsampler.juife.impl;

import android.app.Activity;

public class AndroidPDUtilsImpl implements PDUtilsImpl {
	/** Should be set before using <code>PDUtils</code> */
	public static Activity activity = null;
	
	/**
	 * Causes <code>r.run()</code> to be executed asynchronously on the UI thread.
	 * This call returns immediately.
	 */
	public void
	runOnUiThread(Runnable r) {
		if(activity == null) throw new UnsupportedOperationException("'activity' field should be set before using this method");
		activity.runOnUiThread(r);
	}
	
	/**
	 * Causes <code>r.run()</code> to be executed synchronously on the UI thread.
	 * This call blocks until <code>r.run()</code> returns.
	 */
	public void
	runOnUiThreadAndWait(final Runnable r) throws Exception {
		if(activity == null) throw new UnsupportedOperationException("'activity' field should be set before using this method");
		
		Runnable run = new Runnable() {
			public void run() {
				try { r.run(); }
				catch(Throwable t) { t.printStackTrace(); }
				
				synchronized(this) { this.notify(); }
			}
		};
		
		synchronized(run) {
			activity.runOnUiThread(run);
			run.wait();
		}
	}
}
