package net.thumbtack.school.matrix;

import java.util.*;

public class MatrixNonSimilarRows
{
    private int[][] matrix;

    // Создает MatrixNonSimilarRows по заданной матрице.
    public MatrixNonSimilarRows(int[][] matrix)
    {
        this.matrix = matrix;
    }

    // Возвращает набор непохожих строк.
    public Set<int[]> getNonSimilarRows()
    {
		Set<int[]> retSet = new HashSet<int[]>();
        Map<Set<Integer>, Integer> SetToIndexMap = new HashMap<>();

        for(int i = 0; i < matrix.length; ++i){
            Set<Integer> mapEntry = new HashSet<>();

            for(int j = 0; j < matrix[i].length; ++j){
                mapEntry.add(matrix[i][j]);
            }

            if(SetToIndexMap.containsKey(mapEntry)){
                continue;
            }

            SetToIndexMap.put(mapEntry, i);
		}

		for(Set<Integer> s : SetToIndexMap.keySet()){
		    retSet.add(matrix[SetToIndexMap.get(s)]);
        }

        return retSet;
    }
}
