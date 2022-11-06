/*
 *  JFLAP - Formal Languages and Automata Package
 * 
 * 
 *  Susan H. Rodger
 *  Computer Science Department
 *  Duke University
 *  August 27, 2009

 *  Copyright (c) 2002-2009
 *  All rights reserved.

 *  JFLAP is open source software. Please see the LICENSE for terms.
 *
 */





package gui.action;

import gui.AboutBox;
import java.awt.event.ActionEvent;

/**
 * This action will display a small about box that lists the tool version
 * number, and other version.
 * 
 * @author Thomas Finley
 */

public class AboutAction extends RestrictedAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new <CODE>AboutAction</CODE>.
	 */
	public AboutAction() {
		super("About...", null);
	}

	/**
	 * Shows the about box.
	 */
	public void actionPerformed(ActionEvent e) {
		BOX.displayBox();
	}

	private static final AboutBox BOX = new AboutBox();
}
