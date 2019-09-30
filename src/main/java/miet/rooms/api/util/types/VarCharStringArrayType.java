package miet.rooms.api.util.types;

public class VarCharStringArrayType
        extends StringArrayType {

    public static final VarCharStringArrayType INSTANCE = new VarCharStringArrayType();

    public VarCharStringArrayType() {
        super(VarCharStringArrayTypeDescriptor.INSTANCE);
    }
}
