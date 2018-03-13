package main;

public interface Ability {
    /**
     * Executa abilitatea Defect asupra eroului inamic.
     * @param enemyHero inamicul.
     * @param terrain terenul pe care se desfasoara lupta.
     */
    void action(Hero enemyHero, char terrain);

    /**
     * Incrementeaza procentul abilitatii Deflect cand face level up.
     */
    void levelUp();

    void executeOvertimeAbility(Hero enemyHero, char terrain);

    /**
     * Returneaza 0 pentru ca nu are efect contra unui Wizard, fiind deja abilitate de Wizard.
     * @param terrain terenul pe care se tine lupta.
     * @return returneaza 0.
     */
    float returnPureDamage(char terrain);


}
