package Omega;

/**
 * Created by boveloco on 4/16/17.
 */
public enum Messages {
    BillieWork("VaiTrabalhar"),
    WorkDone("Cabo, vai vc agora seu merda")
    ;

    private final String text;

    /**
     * @param text
     */
    private Messages(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
