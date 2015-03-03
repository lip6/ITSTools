/*******************************************************************************
 * Copyright (c) 2004, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Yann Thierry-Mieg adoption in coloane scenarios (2010), adoption in ITS code base (2015).
 *     J-B Voron, C Desmoulins, M Colange updates to match Coloane api 
 *******************************************************************************/
package fr.lip6.move.gal.itstools.launch;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * Executes an external process synchronously, allowing the client to define a
 * maximum amount of time for the process to complete.
 */
public class ProcessController {
	/**
	 * Thrown when a process being executed exceeds the maximum amount of time
	 * allowed for it to complete.
	 */
	public class TimeOutException extends Exception {
		/**
		 * All serializable objects should have a stable serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Default constructor
		 */
		public TimeOutException() {
		}
		/**
		 * Constructor
		 * @param message the message.
		 */
		public TimeOutException(String message) {
			super(message);
		}
	}

	/** Logger */
	private static final Logger LOGGER = Logger.getLogger("fr.lip6.move.gal"); //$NON-NLS-1$

	private boolean finished;
	private OutputStream forwardStdErr;
	private OutputStream forwardStdOut;
	private boolean killed;
	private String[] params;
	private Process process;
	private long timeLimit;
	private String[] env;
	private File baseDir;

	/**
	 * Constructs an instance of ProcessController.<br>
	 * This does not creates an OS process. <code>run()</code> does that.
	 *
	 * @param timeout The maximum time the process should take to run
	 * @param params The parameters to be passed to the controlled process
	 * @param env The environment vars
	 * @param baseDir The base directory
	 */
	public ProcessController(long timeout, String[] params, String[] env, File baseDir) {
		timeLimit = timeout;
		this.params = params;
		this.env = env;
		this.baseDir = baseDir;
	}

	/**
	 * Causes the process to start executing. This call will block until the
	 * process has completed. If <code>timeout</code> is specified, the
	 * process will be interrupted if it takes more than the specified amount of
	 * time to complete, causing a <code>TimedOutException</code> to be
	 * thrown. Specifying zero as <code>timeout</code> means the process is
	 * not time constrained.
	 *
	 * @return the process exit value
	 * @throws IOException if file problems
	 * @throws TimeOutException If the process did not complete in time
	 */
	public final int execute() throws IOException, TimeOutException {
		Thread waiter = new Thread(new Runnable() {

			public void run() {
				try {
					process = Runtime.getRuntime().exec(params, env, baseDir);
					process.waitFor();
				} catch (InterruptedException e) {
					// timeout !!
					LOGGER.warning(e.getMessage());
				} catch (IOException e) {
					LOGGER.warning(e.getMessage());
				}
			}
		});
		waiter.start();

		long waittime = 100;
		long waitperiods = timeLimit / waittime;
		if (timeLimit == 0) {
			waittime = 0;
			waitperiods = 1;
		}
		try {
			for (int i = 0; i < waitperiods; ++i) {
				// join 0 waits indefinitely
				waiter.join(waittime);
				forwardStreams();
			}
		} catch (InterruptedException e) {
			markFinished();
			forwardStreams();
			return process.exitValue();
		}
		if (waiter.isAlive()) {
			waiter.interrupt();
			kill();
		}
		if (wasKilled()) {
			throw new TimeOutException();
		}
		return process.exitValue();
	}

	/**
	 * Forwards the process standard output and error output.
	 */
	private void forwardStreams() {
		if (process == null)
			return;
		if (forwardStdErr != null) {
			forwardStream("stderr", process.getErrorStream(), forwardStdErr);
		}
		if (forwardStdOut != null) {
			forwardStream("stdout", process.getInputStream(), forwardStdOut);
		}
	}

	/**
	 * Forwards the process standard error output to the given output stream.
	 * Must be called before execution has started.
	 * @param err An output stream where to forward the process standard error output to
	 */
	public final void forwardErrorOutput(OutputStream err) {
		forwardStdErr = err;
	}

	/**
	 * Forwards the process standard output to the given output stream. Must be
	 * called before execution has started.
	 *
	 * @param out An output stream where to forward the process standard output to
	 */
	public final void forwardOutput(OutputStream out) {
		forwardStdOut = out;
	}

	/**
	 * Forward the stream, using a thread, with name "name"
	 * @param name name used to identify this thread
	 * @param in input
	 * @param out output
	 */
	private void forwardStream(final String name, final InputStream in, final OutputStream out) {
		try {
			while (in.available() > 0) {
				out.write(in.read());
			}
			out.flush();
		} catch (IOException e) {
			LOGGER.warning(e.getMessage());
		}
	}

	/**
	 * Returns the controlled process.<br>
	 * Will return <code>null</code> before <code>execute</code> is called.
	 * @return the underlying process
	 */
	public final Process getProcess() {
		return process;
	}

	/**
	 * Test if task is finished
	 * @return true when it is...
	 */
	protected final synchronized boolean isFinished() {
		return finished;
	}

	/**
	 * Kills the process. Does nothing if it has been finished already.
	 */
	public final void kill() {
		if (isFinished()) {
			return;
		}
		killed = true;
		process.destroy();
	}

	/** mark finished state.
	 *
	 */
	private void markFinished() {
		finished = true;
	}

	/**
	 * Returns whether the process was killed due to a time out.
	 * @return <code>true</code> if the process was killed, <code>false</code> if the completed normally
	 */
	public final boolean wasKilled() {
		return killed;
	}
}
