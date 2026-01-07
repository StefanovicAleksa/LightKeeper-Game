package main.game.utilities;

import main.game.domain.models.game.Cell;
import main.game.domain.models.game.CellGrid;
import main.game.domain.models.game.WallCell;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class GameSolver {
    private final int n;
    private final int totalCells;
    private final BitSet walls;
    private final BitSet[] visibility;
    private final List<Integer>[] illuminators;
    private int minBulbs = Integer.MAX_VALUE;
    private long startTime;

    public GameSolver(CellGrid grid) {
        this.n = grid.getCells().length;
        this.totalCells = n * n;

        this.walls = new BitSet(totalCells);
        this.visibility = new BitSet[totalCells];
        this.illuminators = new ArrayList[totalCells];

        for (int i = 0; i < totalCells; i++) {
            illuminators[i] = new ArrayList<>();
            visibility[i] = new BitSet(totalCells);
        }

        Cell[][] cells = grid.getCells();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j] instanceof WallCell) {
                    walls.set(i * n + j);
                }
            }
        }

        precompute();
    }

    public int solve() {
        minBulbs = Integer.MAX_VALUE;
        startTime = System.currentTimeMillis();

        BitSet initialLit = (BitSet) walls.clone();
        BitSet initialBulbs = new BitSet(totalCells);

        search(0, initialBulbs, initialLit);

        return minBulbs == Integer.MAX_VALUE ? -1 : minBulbs;
    }

    private void search(int count, BitSet bulbs, BitSet lit) {
        if (count >= minBulbs) return;

        if (System.currentTimeMillis() - startTime > 3000) return;

        int bestTarget = -1;
        int minCandidates = Integer.MAX_VALUE;
        List<Integer> bestMoves = null;

        for (int i = lit.nextClearBit(0); i >= 0 && i < totalCells; i = lit.nextClearBit(i + 1)) {
            List<Integer> validMoves = new ArrayList<>();
            for (int pos : illuminators[i]) {
                if (canPlace(pos, bulbs)) {
                    validMoves.add(pos);
                }
            }

            if (validMoves.isEmpty()) return;

            if (validMoves.size() < minCandidates) {
                minCandidates = validMoves.size();
                bestTarget = i;
                bestMoves = validMoves;

                if (minCandidates == 1) break;
            }
        }

        if (bestTarget == -1) {
            minBulbs = count;
            return;
        }

        bestMoves.sort((a, b) -> {
            int scoreA = countNewLit(a, lit);
            int scoreB = countNewLit(b, lit);
            return Integer.compare(scoreB, scoreA);
        });

        for (int pos : bestMoves) {
            bulbs.set(pos);

            BitSet nextLit = (BitSet) lit.clone();
            nextLit.or(visibility[pos]);

            search(count + 1, bulbs, nextLit);

            bulbs.clear(pos);
        }
    }

    private boolean canPlace(int pos, BitSet currentBulbs) {
        if (currentBulbs.get(pos)) return false;
        return !currentBulbs.intersects(visibility[pos]);
    }

    private int countNewLit(int pos, BitSet currentLit) {
        BitSet newArea = (BitSet) visibility[pos].clone();
        newArea.andNot(currentLit);
        return newArea.cardinality();
    }

    private void precompute() {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int index = r * n + c;
                if (walls.get(index)) continue;

                BitSet vis = visibility[index];

                vis.set(index);
                castRay(r, c, -1, 0, vis);
                castRay(r, c, 1, 0, vis);
                castRay(r, c, 0, -1, vis);
                castRay(r, c, 0, 1, vis);

                for (int i = vis.nextSetBit(0); i >= 0; i = vis.nextSetBit(i + 1)) {
                    illuminators[i].add(index);
                }
            }
        }
    }

    private void castRay(int r, int c, int dr, int dc, BitSet vis) {
        int i = r + dr;
        int j = c + dc;

        while (i >= 0 && i < n && j >= 0 && j < n) {
            int idx = i * n + j;
            if (walls.get(idx)) break;

            vis.set(idx);
            i += dr;
            j += dc;
        }
    }
}