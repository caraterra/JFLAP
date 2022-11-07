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





package gui.grammar.parse;

import gui.environment.GrammarEnvironment;
import grammar.Grammar;
import grammar.parse.*;
import javax.swing.*;

/**
 * This is a parse pane for LR grammars.
 * 
 * @author Thomas Finley
 */

public class LRParsePane extends ParsePane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiaes a new LR parse pane.
	 * 
	 * @param environment
	 *            the grammar environment
	 * @param grammar
	 *            the augmented grammar
	 * @param table
	 *            the LR parse table
	 */
	public LRParsePane(GrammarEnvironment environment, Grammar grammar,
			LRParseTable table) {
		super(environment, grammar);
		this.table = new LRParseTable(table) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		initView();
	}

	/**
	 * Inits a parse table.
	 * 
	 * @return a table to hold the parse table
	 */
	protected JTable initParseTable() {
		tablePanel = new LRParseTablePane(table);
		return tablePanel;
	}

	/**
	 * This method is called when there is new input to parse.
	 * 
	 * @param string
	 *            a new input string
	 */
	protected void input(String string) {
		controller.initialize(string);
	}

	/**
	 * This method is called when the step button is pressed.
	 */
	protected boolean step() {
		controller.step();
        return true;
	}

	/** The parse table. */
	final LRParseTable table;

	/** The parse table panel. */
	LRParseTablePane tablePanel;

	/** The controller object. */
	protected LRParseController controller = new LRParseController(this);
}
