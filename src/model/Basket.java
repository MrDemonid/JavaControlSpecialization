package model;

import ex.BadWriteLineException;
import ex.BarReadLineException;
import ex.NeverFileException;
import model.fileio.TextFile;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;


public class Basket {


    List<String> food;

    public Basket() {
        food = new ArrayList<>();
    }

    /**
     * Возвращает количество предметов в корзине
     */
    public int getCount()
    {
        return getString().split(" ").length;
    }

    /**
     * Формирует список строк с самыми длинными названиями продуктов
     */
    public List<String> getLongerNames()
    {
        List<String> res = new ArrayList<>();
        LinkedHashMap<Integer, Set<String>> map = getLengthMap();
        if (map != null && !map.isEmpty())
        {
            Set<String> set = getFirstEntry(map).getValue();
            res = set.stream().toList();        // String[] res = set.toArray(new String[0]);
        }
        return res;
    }


    /**
     * Формирует список слов по частоте их вхождения в исходном файле
     */
    public List<String> getFreqList()
    {
        Map<String, Integer> map = getFreqMap();   // получили словарь с частотой слов

        return map.entrySet()
                .stream()
                .map(Map.Entry<String, Integer>::toString)
                .toList();
//        List<String> sorted = map.keySet().stream()
//                .sorted(Comparator.comparing(map::get).reversed())
////                .limit(10)
//                .toList();
    }


    /**
     * Заргузка данных из файла
     * @param fileName Имя файла (файл должен быть в папке assets)
     * @throws NeverFileException
     * @throws BarReadLineException
     */
    public void load(String fileName) throws NeverFileException, BarReadLineException
    {
        TextFile file = new TextFile("assets/" + fileName);
        food = file.load();
    }

    /**
     * Сохранение данных в файл
     * @param fileName Имя файла (файл должен быть в папке assets)
     * @throws NeverFileException
     * @throws BadWriteLineException
     */
    public void save(String fileName) throws NeverFileException, BadWriteLineException
    {
        if (food != null && !food.isEmpty())
        {
            TextFile file = new TextFile("assets/" + fileName);
            file.save(food);
        }
    }


    /* ====================================================================================================
     * Вспомогательные подпрограммы
     * ===================================================================================================*/

    /*
     * Конвертирует список в строку, с удалением лишних пробелов
     */
    private String getString()
    {
        String src = String.join(" ", food);            // String src = food.stream().collect(Collectors.joining(" "));
        return src.replaceAll("\\s+", " ").trim();     // удаляем возможные лишние пробелы
    }

    /*
     * Создаёт отсортированный по длине слов словарь названий продуктов
     */
    private LinkedHashMap<Integer, Set<String>> getLengthMap()
    {
        Map<Integer, Set<String>> map;
        String source = getString();

        Stream<String> words = new Scanner(source).tokens();
        map = words.collect(groupingBy(String::length, toSet()));

        // сортируем map, переводя его в LinkedHashMap
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .collect(LinkedHashMap::new, (lst, elem) -> lst.put(elem.getKey(), elem.getValue()), LinkedHashMap::putAll);
    }

    /*
     * Создаёт словарь частоты вхождений слов, отсортированный по частоте слов
     */
    private LinkedHashMap<String, Integer> getFreqMap()
    {
        String source = getString();

        // и используем поток для создания коллекции (Map), с группировкой данных по словам.
        Stream<String> words = new Scanner(source).tokens();
        Map<String, Long> freq = words.collect(groupingBy(String::toLowerCase, counting()));

        // сортируем map, переводя его в LinkedHashMap
        return freq.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(LinkedHashMap::new, (lst, elem) -> lst.put(elem.getKey(), Math.toIntExact(elem.getValue())), LinkedHashMap::putAll);
    }

    /*
     * Возвращает первый элемент LinkedHashMap<> (реализация не моя)
     */
    private static <K, V> Map.Entry<K, V> getFirstEntry(LinkedHashMap<K, V> map)
    {
        return map.entrySet().iterator().next();
    }

    /*
     * Возвращает последний элемент LinkedHashMap<> (реализация не моя)
     */
    private static <K, V> Map.Entry<K, V> getLastEntry(LinkedHashMap<K, V> map)
    {
        return map.entrySet().stream().reduce((a, b) -> b).orElse(null);
    }


//
//        List<Map.Entry<String, Long>> list = freq.entrySet().stream()
//                .sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue()))
//                .toList();

}
