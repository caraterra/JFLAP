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

import automata.*;
import automata.fsa.FiniteStateAutomaton;
import automata.graph.*;
import automata.graph.layout.GEMLayoutAlgorithm;
import grammar.Production;
import grammar.reg.RightLinearGrammar;
import grammar.reg.RightLinearGrammarToFSAConverter;
import gui.environment.GrammarEnvironment;
import gui.environment.Universe;
import gui.environment.tag.CriticalTag;
import gui.grammar.convert.ConvertPane;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * This is the action that initiates the conversion of right linear grammar to
 * an FSA.
 * 
 * @see gui.grammar.convert.ConvertPane
 * @see grammar.reg.RightLinearGrammarToFSAConverter
 * 
 * @author Thomas Finley
 */

public class ConvertRegularGrammarToFSA extends GrammarAction {
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
	public ConvertRegularGrammarToFSA(GrammarEnvironment environment) {
		super("Convert Right-Linear Grammar to FA", null);
		this.environment = environment;
	}

	/**
	 * Performs the action.
	 */
	public void actionPerformed(ActionEvent e) {
		// Construct the regular grammar.
		RightLinearGrammar grammar = (RightLinearGrammar) environment
				.getGrammar(RightLinearGrammar.class);
		if (grammar == null)
			return;
		if (grammar.getProductions().length == 0) {
			JOptionPane.showMessageDialog(Universe
					.frameForEnvironment(environment),
					"The grammar should exist.");
			return;
		}

		// Create the initial automaton.
		FiniteStateAutomaton fsa = new FiniteStateAutomaton();
		RightLinearGrammarToFSAConverter convert = new RightLinearGrammarToFSAConverter();
		convert.createStatesForConversion(grammar, fsa);
		AutomatonGraph graph = new AutomatonGraph(fsa);
		// Create the map of productions to transitions.
		HashMap<Production, Transition> ptot = new HashMap<>();
		Production[] prods = grammar.getProductions();
		for (int i = 0; i < prods.length; i++) {
			Transition t = convert.getTransitionForProduction(prods[i]);
			graph.addEdge(t.getFromState(), t.getToState());
			ptot.put(prods[i], t);
		}
		// Add the view to the environment.
		final ConvertPane cp = new ConvertPane(grammar, fsa, ptot, environment);
		environment.add(cp, "Convert to FA", new CriticalTag() {
		});
		Rectangle r = cp.getEditorPane().getAutomatonPane().getVisibleRect();
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
