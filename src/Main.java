import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger three = new AtomicInteger(0);
    public static AtomicInteger four = new AtomicInteger(0);
    public static AtomicInteger five = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrome = new Thread (() -> {
            three.getAndAdd(palindrome(texts, 3));
            four.getAndAdd(palindrome(texts, 4));
            five.getAndAdd(palindrome(texts, 5));
        });
        Thread onlyOne = new Thread (() -> {
            three.getAndAdd(onlyOne(texts, 3));
            four.getAndAdd(onlyOne(texts, 4));
            five.getAndAdd(onlyOne(texts, 5));
        });
        Thread ascending = new Thread (() -> {
            three.getAndAdd(ascending(texts, 3));
            four.getAndAdd(ascending(texts, 4));
            five.getAndAdd(ascending(texts, 5));
        });

        palindrome.start();
        onlyOne.start();
        ascending.start();

        palindrome.join();
        onlyOne.join();
        ascending.join();

        System.out.println("Красивых слов с длиной 3: " + three.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + four.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + five.get() + " шт.");


    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

//  Метод подсчета слов палиндромов
    public static int palindrome(String[] texts, int length) {
        int count = 0;
        for (String text : texts) {
            char[] palindrome = text.toCharArray();
            if (palindrome.length == length) {
                if (palindrome[0] == palindrome[length - 1] && palindrome[1] == palindrome[length - 2]) {
                    count++;
                }
            }
        }
        return count;
    }

//  Метод подсчета слов состоящих из одной и той же буквы
    public static int onlyOne(String[] texts, int length) {
        int count = 0;
        for (String text : texts) {
            char[] onlyOne = text.toCharArray();
            if (onlyOne.length == length) {
                boolean only = true;
                for (int i = 0; i < length; i++) {
                    if (onlyOne[0]  != onlyOne[i]) {
                        only = false;
                    }
                }
                if (only) {
                    count++;
                }
            }
        }
        return count;
    }

//  Метод подсчета слов, где буквы в слове идут по возрастанию
    public static int ascending(String[] texts, int length) {
        int count = 0;
        for (String text : texts) {
            char[] ascending = text.toCharArray();
            if (ascending.length == length) {
                boolean abc = true;
                for (int i = 0; i < length - 1; i++) {
                    if (!((int) ascending[i] <= (int) ascending[i + 1])) {
                        abc = false;
                    }
                }
                if (abc) {
                    count++;
                }
            }
        }
        return count;
    }
}