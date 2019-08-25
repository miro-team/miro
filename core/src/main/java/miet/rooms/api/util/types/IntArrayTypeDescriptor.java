package miet.rooms.api.util.types;

public class IntArrayTypeDescriptor
        extends AbstractArrayTypeDescriptor<int[]> {

    public static final IntArrayTypeDescriptor INSTANCE = new IntArrayTypeDescriptor();

    public IntArrayTypeDescriptor() {
        super(int[].class);
    }

    @Override
    public String getSqlArrayType() {
        return "integer";
    }
}