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

import grammar.CNFConverter;
import grammar.Grammar;
import grammar.LambdaProductionRemover;
import grammar.Production;
import grammar.UnitProductionRemover;
import grammar.UselessProductionRemover;
import gui.environment.EnvironmentFrame;
import gui.environment.GrammarEnvironment;
import gui.environment.Universe;
import gui.environment.tag.CriticalTag;
import gui.grammar.transform.ChomskyPane;
import gui.grammar.transform.LambdaPane;
import gui.grammar.transform.UnitPane;
import gui.grammar.transform.UselessPane;

import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * This is an action to transform a grammar.
 * 
 * @author Thomas Finley
 */

public class GrammarTransformAction extends GrammarAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new <CODE>GrammarTransformAction</CODE>.
	 * 
	 * @param environment
	 *            the grammar environment
	 */
	public GrammarTransformAction(GrammarEnvironment environment) {
		super("Transform Grammar", null);
		this.environment = environment;
		this.frame = Universe.frameForEnvironment(environment);
	}

	/**
	 * Performs the action.
	 */
	public void actionPerformed(ActionEvent e) {
		Grammar g = environment.getGrammar();
		if (g == null)
			return;
		hypothesizeLambda(environment, g);
	}

	// Changed to support the Lambda removal for Grammar, which Start variable derives Lambda.
	public static void hypothesizeLambda(GrammarEnvironment env, Grammar g) {
		LambdaProductionRemover remover = new LambdaProductionRemover();
		Set<String> lambdaDerivers = remover.getCompleteLambdaSet(g);
	    if (lambdaDerivers.contains(g.getStartVariable())) {
			JOptionPane.showMessageDialog(env,
					"WARNING : The start variable derives lambda.\n"
							+ "New Grammar will not produce lambda String.",
					"Start Derives Lambda", JOptionPane.ERROR_MESSAGE);
		} 
		if (lambdaDerivers.size() > 0) {
			LambdaPane lp = new LambdaPane(env, g);
			env.add(lp, "Lambda Removal", new CriticalTag() {
			});
			env.setActive(lp);
			return;
		}
		hypothesizeUnit(env, g);
	}

	public static void hypothesizeUnit(GrammarEnvironment env, Grammar g) {
		UnitProductionRemover remover = new UnitProductionRemover();
		if (remover.getUnitProductions(g).length > 0) {
			UnitPane up = new UnitPane(env, g);
			env.add(up, "Unit Removal", new CriticalTag() {
			});
			env.setActive(up);
			return;
		}
		hypothesizeUseless(env, g);
	}

	public static void hypothesizeUseless(GrammarEnvironment env, Grammar g) {
		UselessProductionRemover remover = new UselessProductionRemover();
		/*
		 * Grammar g2 = remover.getUselessProductionlessGrammar(g); if
		 * (g2.getProductions().length < g.getProductions().length) {
		 * UselessPane up = new UselessPane(env, g); env.add(up, "Useless
		 * Removal", new CriticalTag() {}); env.setActive(up); return; }
		 * hypothesizeChomsky(env, g);
		 */
		Grammar g2 = UselessProductionRemover
				.getUselessProductionlessGrammar(g);
		Production[] p1 = g.getProductions();
		Production[] p2 = g2.getProductions();
		if (p1.length > p2.length) {
			UselessPane up = new UselessPane(env, g);
			env.add(up, "Useless Removal", new CriticalTag() {
			});
			env.setActive(up);
			return;
		}
		hypothesizeChomsky(env, g);
	}

	public static void hypothesizeChomsky(GrammarEnvironment env, Grammar g) {
		CNFConverter converter = null;
		try {
			converter = new CNFConverter(g);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(env, e.getMessage(),
					"Illegal Grammar", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Production[] p = g.getProductions();
		boolean chomsky = true;
		for (int i = 0; i < p.length; i++)
			chomsky &= converter.isChomsky(p[i]);
		if (chomsky) {
			JOptionPane.showMessageDialog(env,
					"The grammar is already in Chomsky NF.", "Already in CNF",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		ChomskyPane cp = new ChomskyPane(env, g);
		env.add(cp, "Chomsky Converter", new CriticalTag() {
		});
		env.setActive(cp);
	}

	/** The grammar environment. */
	private GrammarEnvironment environment;

	/** The frame for the grammar environment. */
	private EnvironmentFrame frame;
}
