package com.filter.textcorrector.text_preproccessing;

import com.filter.textcorrector.text_preproccessing.util.CleanTextType;
import com.filter.textcorrector.text_preproccessing.util.TextUtils;

import java.util.regex.Pattern;

//TODO: possibly make it an object to load resources once.
public class TextPreproccessor {
    private static final SymbolMapper symbolMapper = new SymbolMapper();

    //TODO: Why does it take so much time?
    public static String preproccess(String text){

        //1ms for 600 words
        text = symbolMapper.mapNumbers(text);

        //8ms for 600 words.
        //TODO: possibly we don't need this.
        text = TextUtils.cleanText(text, CleanTextType.SPACES_BETWEEN_SINGLE_LETTERS);

        String correctedText = text;

        //15-16ms for 600 words.
        String[] originalWords = TextUtils.splitCleanText(correctedText, CleanTextType.SPLIT_WITHOUT_CLEANING);

        //110ms for 600 words.
        for (int j = 0; j < originalWords.length; j++) {
            String word = originalWords[j];
            String possibleDigit = TextUtils.cleanText(word, CleanTextType.CLEAR_PUNCTUATION);

            if(TextUtils.isWordDigit(possibleDigit.substring(0, possibleDigit.length() > 0 ? possibleDigit.length() - 1 : 0))){
                continue;
            }

            String transliteratedWord = symbolMapper.mapCharacters(word);

            if(!hasSpecialChar(originalWords[j])){
                correctedText = TextUtils.replaceWord(correctedText, originalWords[j], transliteratedWord);
            }
            else {
                correctedText = correctedText.replace(originalWords[j], transliteratedWord);
            }
        }

        //3ms for 600 words.
        correctedText = cleanText(correctedText);

        return correctedText;
    }

    private static boolean hasSpecialChar(String originalWord) {
        Pattern p = Pattern.compile("[^a-zA-Z0-9_]");
        return p.matcher(originalWord).find();
    }

    private static String cleanText(String text) {
        text = TextUtils.cleanText(text, CleanTextType.PUNCTUATION_BETWEEN_SINGLE_LETTERS);
        text = TextUtils.cleanText(text, CleanTextType.WHITE_SPACES);
        text = TextUtils.cleanText(text, CleanTextType.SYMBOLS_IN_WORDS);
        text = TextUtils.cleanText(text, CleanTextType.DIGITS_IN_WORDS);
        return text;
    }

    public static void main(String[] args) {
        System.out.println(TextPreproccessor.preproccess("Evan Lambert 37.9k votes 7.7k voters 465.6k views push-up and 7k 38 items Follow Embed"));
    }
}
