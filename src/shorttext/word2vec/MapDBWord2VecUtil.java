package shorttext.word2vec;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.io.File;
import java.util.Map;

/**
 * Created by hok on 1/19/17.
 */
public class MapDBWord2VecUtil implements Word2VecUtil {
    Map<String, double[]> word2VecMap;

    public MapDBWord2VecUtil(File mapDBFile) {
        DB db = DBMaker.newFileDB(mapDBFile).make();
        this.word2VecMap = db.getHashMap("map");
    }

    @Override
    public double[] getWordVector(String word) {
        return word2VecMap.get(word);
    }

    @Override
    public INDArray getWordMatrix(String word) {
        return Nd4j.create(getWordVector(word));
    }

    @Override
    public double similarity(String word1, String word2) {
        // native ND4J implementation need a native backend. Use primitive method here.
        double[] vec1 = getWordVector(word1);
        double[] vec2 = getWordVector(word2);

        double cosineSum = 0;
        double vec1SqNorm = 0;
        double vec2SqNorm = 0;

        for (int i=0; i<vec1.length; i++) {
            cosineSum += vec1[i]*vec2[i];
            vec1SqNorm += vec1[i]*vec1[i];
            vec2SqNorm += vec2[i]*vec2[i];
        }

        return cosineSum/Math.sqrt(vec1SqNorm*vec2SqNorm);
    }
}
