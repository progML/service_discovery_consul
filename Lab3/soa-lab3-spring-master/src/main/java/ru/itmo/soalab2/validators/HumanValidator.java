package ru.itmo.soalab2.validators;

import ru.itmo.soalab2.model.Error;
import ru.itmo.soalab2.model.Human;
import ru.itmo.soalab2.model.HumanFromClient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HumanValidator {
    public Human validate(HumanFromClient human) throws IllegalAccessException, ValidateFieldsException {
        List<Error> errorList = new ArrayList<>();
        Human validatedHuman = new Human();

        if (human == null) {
            throw new ValidateFieldsException(errorList);
        }

        for (Field f : HumanFromClient.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(human) == null) {
                errorList.add(new Error(700, f.getName(), String.format("Human %s is not specified", f.getName())));
            }
        }

        try {
            Double height = 0D;
            if (human.getHeight() != null) {
                height = Double.parseDouble(human.getHeight());
            }
            if (height <= 0) {
                errorList.add(new Error(701, "x", "Coordinates y should be bigger than -60"));
            } else  {
                validatedHuman.setHeight(height);
            }
        } catch (NumberFormatException e) {
            errorList.add(new Error(702, "height", "The entered value is not a double value"));
        }

        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }
        validatedHuman.setBirthday(human.getBirthday());
        if (human.getId() != 0) validatedHuman.setId(human.getId());
        return validatedHuman;
    }
}
