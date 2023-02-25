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

public class DefaultPDUtilsImpl implements PDUtilsImpl {
	/**
	 * Causes <code>r.run()</code> to be executed asynchronously on the UI thread.
	 * This call returns immediately.
	 */
	public void
	runOnUiThread(Runnable r) {
		try {
			Class.forName("javax.swing.SwingUtilities")
				.getMethod("invokeLater", Runnable.class)
				.invoke(null, r);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Causes <code>r.run()</code> to be executed synchronously on the UI thread.
	 * This call blocks until <code>r.run()</code> returns.
	 */
	public void
	runOnUiThreadAndWait(Runnable r) throws Exception {
		try {
			Class.forName("javax.swing.SwingUtilities")
				.getMethod("invokeAndWait", Runnable.class)
				.invoke(null, r);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
