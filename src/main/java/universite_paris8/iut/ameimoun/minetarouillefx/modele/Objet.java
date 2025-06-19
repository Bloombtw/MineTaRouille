package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public enum Objet {
 EPEE(101, "Épée", 1, "Une épée pour combattre les monstres.", Type.ARME, Rarete.COMMUN),
    ARC(102, "Arc", 1, "Un arc pour tirer des flèches à distance.", Type.ARME, Rarete.COMMUN),
    PIOCHE(103, "Pioche", 1, "Une pioche pour miner les blocs.", Type.OUTIL, Rarete.COMMUN),
    PELLE(104, "Pelle", 1, "Une pelle pour creuser la terre et le sable.", Type.OUTIL, Rarete.COMMUN),
    MOUTON_CUIT(105, "Mouton cuit", 64, "Un délicieux mouton rôti.", Type.CONSOMMABLE, Rarete.COMMUN),
    BATON(106, "Bâton", 64, "Un bâton utile pour fabriquer des outils.", Type.RESSOURCE, Rarete.COMMUN),
    FIL(107,"Fil", 64, "Un fil utilisé pour fabriquer un arc.", Type.RESSOURCE, Rarete.COMMUN),
    LIVRE(108, "Livre", 1, "Le livre d'introduction apprenant les touches nécessaires au jeu.", Type.RESSOURCE, Rarete.LEGENDAIRE);
    private final int id;
    private final String nom;
    private final int stackSize;
    private final String description;
    private final Type type;
    private final Rarete rarete;

    Objet(int id, String nom, int stackSize, String description, Type type, Rarete rarete) {
        this.id = id;
        this.nom = nom;
        this.stackSize = stackSize;
        this.description = description;
        this.type = type;
        this.rarete = rarete;
    }
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public int getStackSize() {
        return stackSize;
    }
    public String getDescription() {
        return description;
    }
    public Type getType() {
        return type;
    }
    public Rarete getRarete() {
        return rarete;
    }

    public boolean estUnLivre(Item itemAComparer) {
        return itemAComparer.getId() == 108;
    }
}
