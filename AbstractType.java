abstract class AbstractType {
    private String typeName;
    /**
     * @param typeName

     */

    public AbstractType(String typeName) {
        this.typeName = typeName;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
