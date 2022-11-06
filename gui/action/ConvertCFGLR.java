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

import automata.Transition;
import automata.graph.*;
import automata.graph.layout.GEMLayoutAlgorithm;
import automata.pda.PushdownAutomaton;
import grammar.Grammar;
import grammar.Production;
import grammar.cfg.CFGToPDALRConverter;
import gui.environment.GrammarEnvironment;
import gui.environment.Universe;
import gui.environment.tag.CriticalTag;
import gui.grammar.convert.ConvertPane;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * This is the action that initiates the conversion of a context free grammar to
 * a PDA using LR conversion.
 * 
 * @author Thomas Finley
 */

public class ConvertCFGLR extends GrammarAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new <CODE>GrammarOutputAction</CODE>.
	 * 
	 * @param environment
	 *            the grammar environment
	 */
	public ConvertCFGLR(GrammarEnvironment environment) {
		super("Convert CFG to PDA (LR)", null);
		this.environment = environment;
	}

	/**
	 * Performs the action.
	 */
	public void actionPerformed(ActionEvent e) {
		Grammar grammar = environment.getGrammar();
		if (grammar == null)
			return;
		if (grammar.getProductions().length == 0) {
			JOptionPane.showMessageDialog(Universe
					.frameForEnvironment(environment),
					"The grammar should exist.");
			return;
		}
		// Create the initial automaton.
		PushdownAutomaton pda = new PushdownAutomaton();
		CFGToPDALRConverter convert = new CFGToPDALRConverter();
		convert.createStatesForConversion(grammar, pda);
		// Create the map of productions to transitions.
		HashMap<Production, Transition> ptot = new HashMap<>();
		Production[] prods = grammar.getProductions();
		for (int i = 0; i < prods.length; i++)
			ptot.put(prods[i], convert.getTransitionForProduction(prods[i]));
		// Add the view to the environment.
		final ConvertPane cp = new ConvertPane(grammar, pda, ptot, environment);
		environment.add(cp, "Convert to PDA (LR)", new CriticalTag() {
		});

		// Do the layout of the states.
		AutomatonGraph graph = new AutomatonGraph(pda);
		LayoutAlgorithm layout = new GEMLayoutAlgorithm();
		layout.layout(graph, null);
		graph.moveAutomatonStates();
		environment.setActive(cp);
		environment.validate();
		cp.getEditorPane().getAutomatonPane().fitToBounds(20);
	}

	/** The grammar environment. */
	private GrammarEnvironment environment;
}
