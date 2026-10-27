package com.epam.training.food;

public class ValidateISBN {
    public boolean checkISBN(String isbn) {
        if (isbn.length() != 10) {
            throw new NumberFormatException();
        }

        if (isbn.substring(0,9).chars().anyMatch(Character::isLetter)){
            throw new NumberFormatException();
        }

        int total = 0;
        for (int i = 0; i < isbn.length(); i++) {
            if (i == 9 && (
                    Character.isLetter(isbn.charAt(i))
                            &&
                            isbn.charAt(i) != 'X'
            )) {
                System.out.println(isbn.charAt(i));
                total +=10;
                throw new NumberFormatException();
            }else{
                total += isbn.charAt(i) *(10-i);
            }

        }

        return total % 11 == 0;
    }
}
