package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class WordNet {
    private Graph<Integer> hyponymsGraph;
    private HashMap<Integer, HashSet<String>> idToSynset;
    private HashMap<String, HashSet<Integer>> wordToIds;

    private NGramMap ngm;

    public WordNet(String hyponymsFilename, String synsetsFilename, NGramMap ngm) {
        // Read hyponyms file and store the data in hyponymsGraph
        hyponymsGraph = new Graph<>();
        In hyponyms = new In(hyponymsFilename);
        while (!hyponyms.isEmpty()) {
            // 5,6,7 -> ["5", "6", "7"]
            String[] field = hyponyms.readLine().split(",");
            // where the first element 5 is the synset id
            int synsetId = Integer.parseInt(field[0]);
            // and the following elements are the hyponyms
            for (int i = 1; i < field.length; i++) {
                int hypoId = Integer.parseInt(field[i]);
                hyponymsGraph.addEdge(synsetId, hypoId);
            }
        }

        // Read synsets file and store the data in synsetsGraph
        idToSynset = new HashMap<>();
        wordToIds = new HashMap<>();
        In synsets = new In(synsetsFilename);
        while (!synsets.isEmpty()) {
            // 4,jump parachuting,dummy -> ["4", "jump parachuting", "dummy"]
            String[] field = synsets.readLine().split(",");
            // where the first element is the synset id
            int synsetId = Integer.parseInt(field[0]);
            // and the second element is the synset that can contains multiple words
            String[] synset = field[1].split(" ");
            for (String w : synset) {
                if (!idToSynset.containsKey(synsetId)) {
                    idToSynset.put(synsetId, new HashSet<>());
                }
                idToSynset.get(synsetId).add(w);

                if (!wordToIds.containsKey(w)) {
                    wordToIds.put(w, new HashSet<>());
                }
                wordToIds.get(w).add(synsetId);
            }
        }

        this.ngm = ngm;
    }

    public WordNet(String hyponymsFilename, String synsetsFilename) {
        this(hyponymsFilename, synsetsFilename, null);
    }

    private class Word implements Comparable<Word> {
        private String value;
        private double pop;

        public Word(String value, double pop) {
            this.value = value;
            this.pop = pop;
        }

        public double getPop() {
            return pop;
        }
        @Override
        public int compareTo(Word other) {
            if (this.getPop() > other.getPop()) {
                return -1;
            } else if (this.getPop() == other.getPop()) {
                return 0;
            } else {
                return 1;
            }
        }
    }
    public TreeSet<String> hyponymsOf(List<String> words, int k, int startYear, int endYear) {
        TreeSet<String> hyponyms = hyponymsOf(words);
        if (k == 0) {
            return hyponyms;
        }

        PriorityQueue<Word> pq = new PriorityQueue<>();
        for (String word: hyponyms) {
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            List<Double> data = ts.data();
            double pop = 0;
            for (double count: data) {
                pop += count;
            }
            pq.add(new Word(word, pop));
        }

        TreeSet<String> topKHyponyms = new TreeSet<String>();
        for (int i = 0; i < k; i++) {
            if (pq.isEmpty()) {
                break;
            }
            topKHyponyms.add(pq.poll().value);
        }
        return topKHyponyms;
    }

    public TreeSet<String> hyponymsOf(List<String> words) {
        HashSet<Integer> ids = wordToIds.get(words.get(0));
        TreeSet<String> hyponyms = new TreeSet<>();
        for (int id: ids) {
            traverse(id, hyponyms);
        }

        for (int i = 1; i < words.size(); i++) {
            HashSet<Integer> nIds = wordToIds.get(words.get(i));
            TreeSet<String> nHyponyms = new TreeSet<>();
            for (int id: nIds) {
                traverse(id, nHyponyms);
            }
            hyponyms.retainAll(nHyponyms);
        }
        return hyponyms;
    }

    private void traverse(Integer node, TreeSet<String> hyponyms) {
        // Read the current node and add all the words in the current node to the hyponyms set
        HashSet<String> synset = idToSynset.get(node);
        for (String w: synset) {
            hyponyms.add(w);
        }

        // Traverse all the children nodes
        for (Integer neighbor: hyponymsGraph.neighbors(node)) {
            traverse(neighbor, hyponyms);
        }
    }
}
