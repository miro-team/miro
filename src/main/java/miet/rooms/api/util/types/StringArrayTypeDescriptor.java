package miet.rooms.api.util.types;

public class StringArrayTypeDescriptor
        extends AbstractArrayTypeDescriptor<String[]> {

    public static final StringArrayTypeDescriptor INSTANCE = new StringArrayTypeDescriptor();

    public StringArrayTypeDescriptor() {
        super(String[].class);
    }

    @Override
    public String getSqlArrayType() {
        return "text";
    }
}