package Students;

import Exceptions.InvalidStudentType;

import java.io.Serial;
import java.io.Serializable;

public enum StudentTypeEnum implements Serializable {
    BOOKISH,
    OUTGOING,
    THRIFTY;

    @Serial
    private static final long serialVersionUID = 18L;

    /**
     * Gives the type of the student if it's a valid type(BOOKISH, OUTGOING, THRIFTY)
     * @param type: Type of the student
     * @return the Enum with the type given
     * @throws InvalidStudentType if the given type is not a valid type
     */
    public static StudentTypeEnum getValue(String type){
        try{
            return StudentTypeEnum.valueOf(type.toUpperCase());
        }catch (Exception e){
            throw new InvalidStudentType();
        }
    }
}
