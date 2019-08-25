package miet.rooms.api.util.types;

public class VarCharStringArrayTypeDescriptor
        extends StringArrayTypeDescriptor {

    public static final VarCharStringArrayTypeDescriptor INSTANCE =
            new VarCharStringArrayTypeDescriptor();

    @Override
    public String getSqlArrayType() {
        return "varchar";
    }
}
