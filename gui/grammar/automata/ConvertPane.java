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





package gui.grammar.automata;

import automata.Automaton;
import gui.SplitPaneFactory;
import gui.TableTextSizeSlider;
import gui.editor.ArrowDisplayOnlyTool;
import gui.environment.AutomatonEnvironment;
import gui.grammar.GrammarTable;
import gui.viewer.AutomatonPane;
import gui.viewer.SelectionDrawer;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;

/**
 * This <CODE>ConvertPane</CODE> exists for the user to convert an automaton
 * to a grammar.
 * 
 * @author Thomas Finley
 */

public class ConvertPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new <CODE>ConvertPane</CODE>.
	 */
	public ConvertPane(AutomatonEnvironment environment, Automaton automaton) {
		super(new BorderLayout());
		drawer = new SelectionDrawer(automaton);
		automatonPane = new AutomatonPane(drawer);
		JSplitPane split = SplitPaneFactory.createSplit(environment, true, 0.6,
				automatonPane, new JScrollPane(table));
		automatonPane.addMouseListener(new ArrowDisplayOnlyTool(automatonPane,
				automatonPane.getDrawer()));
		add(split, BorderLayout.CENTER);
		add(new TableTextSizeSlider(table, JSlider.HORIZONTAL), BorderLayout.SOUTH);
	}

	/**
	 * Returns the <CODE>AutomatonPane</CODE> that does the drawing.
	 * 
	 * @return the <CODE>AutomatonPane</CODE> that does the drawing
	 */
	public AutomatonPane getAutomatonPane() {
		return automatonPane;
	}

	/**
	 * Returns the <CODE>SelectionDrawer</CODE> for the automaton pane.
	 * 
	 * @return the <CODE>SelectionDrawer</CODE>
	 */
	public SelectionDrawer getDrawer() {
		return drawer;
	}

	/**
	 * Returns the <CODE>GrammarTable</CODE> where the grammar is being built.
	 * 
	 * @return the <CODE>GrammarTable</CODE>
	 */
	public GrammarTable getTable() {
		return table;
	}

	/** The automaton pane. */
	private AutomatonPane automatonPane;

	/** The grammar table. */
	private GrammarTable table = new GrammarTable(
			new gui.grammar.GrammarTableModel() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int r, int c) {
					return false;
				}
			});

	/** The drawer for the automaton. */
	private SelectionDrawer drawer;
}
