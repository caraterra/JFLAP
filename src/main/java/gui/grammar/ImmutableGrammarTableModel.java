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





package gui.grammar;

import grammar.Grammar;

/**
 * The <CODE>ImmutableGrammarTableModel</CODE> is a grammar table model that
 * cannot be changed.
 * 
 * @see grammar.Production
 * 
 * @author Thomas Finley
 */

public class ImmutableGrammarTableModel extends GrammarTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a <CODE>GrammarTableModel</CODE>.
	 */
	public ImmutableGrammarTableModel() {
		super();
	}

	/**
	 * Instantiates a <CODE>GrammarTableModel</CODE>.
	 * 
	 * @param grammar
	 *            the grammar to have for the grammar table model initialized to
	 */
	public ImmutableGrammarTableModel(Grammar grammar) {
		super(grammar);
	}

	/**
	 * No cell is editable in this model.
	 * 
	 * @param row
	 *            the index for the row
	 * @param column
	 *            the index for the column
	 * @return <CODE>false</CODE> always
	 */
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
