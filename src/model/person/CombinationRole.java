package model.person;

import model.goals.GoalsImpl;
import model.relationship.IRelationship;

import java.util.List;

class CombinationRole extends ARole {

	private final RomanticRole romantic;
	private final PlatonicRole platonic;
	private final CareerRole career;
	private final NarrativeRole narrative;

	CombinationRole(String title, float strength, RomanticRole romantic, PlatonicRole platonic, CareerRole career,
			NarrativeRole narrative) {
		super(title, strength);
		this.romantic = romantic;
		this.platonic = platonic;
		this.career = career;
		this.narrative = narrative;
	}

	CombinationRole(RomanticRole romantic, PlatonicRole platonic, CareerRole career, NarrativeRole narrative) {
		super("", 0);
		this.romantic = romantic;
		this.platonic = platonic;
		this.career = career;
		this.narrative = narrative;
		this.title = calculateTitle();
		this.strength = calculateStrength();
	}

	private void distributeActionPoints() {
		int actionPoints = this.narrative.getActionPoints();
		int platonicAP = (int)(this.platonic.strength/this.strength * actionPoints);
		int romanticAP = (int) (this.romantic.strength/this.strength * actionPoints);
		int careerAP = (int) (this.career.strength/this.strength * actionPoints);
		this.platonic.setActionPoints(platonicAP);
	}

	private float calculateStrength() {
		return (this.romantic.strength + this.platonic.strength + this.career.strength + this.narrative.strength) / 4;
	}

	private String calculateTitle() {
		return String.join(";", romantic.title, platonic.title, career.title, narrative.title);
	}

	ARole merge(ARole r2) {
		return r2.mergeCoR(this);
	}

	@Override
	public double computeRomanticCompatibility(ARole r) {
		if (r instanceof CombinationRole) {
			return this.romantic.computeRomanticCompatibility(((CombinationRole) r).romantic);
		}
		return this.romantic.computeRomanticCompatibility(r);
	}

	protected ARole mergeCoR(CombinationRole cr) {
		RomanticRole romantic = (RomanticRole) this.romantic.merge(cr.romantic);
		PlatonicRole platonic = (PlatonicRole) this.platonic.merge(cr.platonic);
		CareerRole career = (CareerRole) this.career.merge(cr.career);
		NarrativeRole narrative = (NarrativeRole) this.narrative.merge(cr.narrative);
		return new CombinationRole(romantic, platonic, career, narrative);
	}

	public void setGoals(GoalsImpl goals, List<IRelationship> relationships, IRelationship significantOther) {
		this.platonic.setGoals(goals, relationships);
		this.romantic.setGoals(goals, relationships, significantOther);
	}

}
