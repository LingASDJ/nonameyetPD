
package com.watabou.utils;

import java.util.Arrays;
import java.util.LinkedList;

public class PathFinder {

	public static int[] distance;
	private static int[] maxVal;

	private static boolean[] goals;
	private static int[] queue;

	private static int size = 0;
	private static int width = 0;

	private static int[] dir;
	private static int[] dirLR;

	public static int[] NEIGHBOURS4;
	public static int[] NEIGHBOURS8;
	public static int[] NEIGHBOURS9;
	public static int[] NEIGHBOURS8DIST2;
	public static int[] NEIGHBOURS9DIST2;

	public static int[] CIRCLE4;
	public static int[] CIRCLE8;

	public static int[] CIRCLE;

	public static void setMapSize(int width, int height) {

		PathFinder.width = width;
		PathFinder.size = width * height;

		distance = new int[size];
		goals = new boolean[size];
		queue = new int[size];

		maxVal = new int[size];
		Arrays.fill(maxVal, Integer.MAX_VALUE);

		dir = new int[]{-1, +1, -width, +width, -width - 1, -width + 1, +width - 1, +width + 1};
		dirLR = new int[]{-1-width, -1, -1+width, -width, +width, +1-width, +1, +1+width};

		NEIGHBOURS4 = new int[]{-width, +1, +width, -1};
		NEIGHBOURS8 = new int[]{+1, -1, +width, -width,
				+1 + width, +1 - width, -1 + width, -1 - width};
		NEIGHBOURS9 = new int[]{0, +1, -1, +width, -width,
				+1 + width, +1 - width, -1 + width, -1 - width};

		// Note that use of these without checking values is unsafe, mobs can be
		// within 2 tiles of the
		// edge of the map, unsafe use in that case will cause an array out of
		// bounds exception.
		NEIGHBOURS8DIST2 = new int[]{+2 + 2 * width,
				+1 + 2 * width, 2 * width, -1 + 2 * width, -2 + 2 * width,
				+2 + width, +1 + width, +width, -1 + width, -2 + width, +2, +1, -1,
				-2, +2 - width, +1 - width, -width, -1 - width, -2 - width,
				+2 - 2 * width, +1 - 2 * width, -2 * width, -1 - 2 * width,
				-2 - 2 * width};
		NEIGHBOURS9DIST2 = new int[]{+2 + 2 * width,
				+1 + 2 * width, 2 * width, -1 + 2 * width, -2 + 2 * width,
				+2 + width, +1 + width, +width, -1 + width, -2 + width, +2, +1, 0,
				-1, -2, +2 - width, +1 - width, -width, -1 - width, -2 - width,
				+2 - 2 * width, +1 - 2 * width, -2 * width, -1 - 2 * width,
				-2 - 2 * width};

		CIRCLE = new int[]{-width - 1, -width, -width + 1, +1, +width + 1, +width, +width - 1, -1};
	}

	public static Path find(int from, int to, boolean[] passable) {

		if (!buildDistanceMap(from, to, passable)) {
			return null;
		}

		Path result = new Path();
		int s = from;

		// From the starting position we are moving downwards,
		// until we reach the ending point
		do {
			int minD = distance[s];
			int mins = s;

			for (int i = 0; i < dir.length; i++) {

				int n = s + dir[i];

				int thisD = distance[n];
				if (thisD < minD) {
					minD = thisD;
					mins = n;
				}
			}
			s = mins;
			result.add(s);
		} while (s != to);

		return result;
	}

	public static int getStep(int from, int to, boolean[] passable) {

		if (!buildDistanceMap(from, to, passable)) {
			return -1;
		}

		// From the starting position we are making one step downwards
		int minD = distance[from];
		int best = from;

		int step, stepD;

		for (int i = 0; i < dir.length; i++) {

			if ((stepD = distance[step = from + dir[i]]) < minD) {
				minD = stepD;
				best = step;
			}
		}

		return best;
	}

	public static int getStepBack(int cur, int from, boolean[] passable) {

		int d = buildEscapeDistanceMap(cur, from, 2f, passable);
		for (int i = 0; i < size; i++) {
			goals[i] = distance[i] == d;
		}
		if (!buildDistanceMap(cur, goals, passable)) {
			return -1;
		}

		int s = cur;

		// From the starting position we are making one step downwards
		int minD = distance[s];
		int mins = s;

		for (int i = 0; i < dir.length; i++) {

			int n = s + dir[i];
			int thisD = distance[n];

			if (thisD < minD) {
				minD = thisD;
				mins = n;
			}
		}

		return mins;
	}

	private static boolean buildDistanceMap(int from, int to, boolean[] passable) {

		if (from == to) {
			return false;
		}

		System.arraycopy(maxVal, 0, distance, 0, maxVal.length);

		boolean pathFound = false;

		int head = 0;
		int tail = 0;

		// Add to queue
		queue[tail++] = to;
		distance[to] = 0;

		while (head < tail) {

			// Remove from queue
			int step = queue[head++];
			if (step == from) {
				pathFound = true;
				break;
			}
			int nextDistance = distance[step] + 1;

			int start = (step % width == 0 ? 3 : 0);
			int end   = ((step+1) % width == 0 ? 3 : 0);
			for (int i = start; i < dirLR.length - end; i++) {

				int n = step + dirLR[i];
				if (n == from || (n >= 0 && n < size && passable[n] && (distance[n] > nextDistance))) {
					// Add to queue
					queue[tail++] = n;
					distance[n] = nextDistance;
				}

			}
		}

		return pathFound;
	}

	public static void buildDistanceMap(int to, boolean[] passable, int limit) {

		System.arraycopy(maxVal, 0, distance, 0, maxVal.length);

		int head = 0;
		int tail = 0;

		// Add to queue
		queue[tail++] = to;
		distance[to] = 0;

		while (head < tail) {

			// Remove from queue
			int step = queue[head++];

			int nextDistance = distance[step] + 1;
			if (nextDistance > limit) {
				return;
			}

			int start = (step % width == 0 ? 3 : 0);
			int end   = ((step+1) % width == 0 ? 3 : 0);
			for (int i = start; i < dirLR.length - end; i++) {

				int n = step + dirLR[i];
				if (n >= 0 && n < size && passable[n] && (distance[n] > nextDistance)) {
					// Add to queue
					queue[tail++] = n;
					distance[n] = nextDistance;
				}

			}
		}
	}

	private static boolean buildDistanceMap(int from, boolean[] to, boolean[] passable) {

		if (to[from]) {
			return false;
		}

		System.arraycopy(maxVal, 0, distance, 0, maxVal.length);

		boolean pathFound = false;

		int head = 0;
		int tail = 0;

		// Add to queue
		for (int i = 0; i < size; i++) {
			if (to[i]) {
				queue[tail++] = i;
				distance[i] = 0;
			}
		}

		while (head < tail) {

			// Remove from queue
			int step = queue[head++];
			if (step == from) {
				pathFound = true;
				break;
			}
			int nextDistance = distance[step] + 1;

			int start = (step % width == 0 ? 3 : 0);
			int end   = ((step+1) % width == 0 ? 3 : 0);
			for (int i = start; i < dirLR.length - end; i++) {

				int n = step + dirLR[i];
				if (n == from || (n >= 0 && n < size && passable[n] && (distance[n] > nextDistance))) {
					// Add to queue
					queue[tail++] = n;
					distance[n] = nextDistance;
				}

			}
		}

		return pathFound;
	}

	private static int buildEscapeDistanceMap(int cur, int from, float factor, boolean[] passable) {

		System.arraycopy(maxVal, 0, distance, 0, maxVal.length);

		int destDist = Integer.MAX_VALUE;

		int head = 0;
		int tail = 0;

		// Add to queue
		queue[tail++] = from;
		distance[from] = 0;

		int dist = 0;

		while (head < tail) {

			// Remove from queue
			int step = queue[head++];
			dist = distance[step];

			if (dist > destDist) {
				return destDist;
			}

			if (step == cur) {
				destDist = (int) (dist * factor) + 1;
			}

			int nextDistance = dist + 1;

			int start = (step % width == 0 ? 3 : 0);
			int end   = ((step+1) % width == 0 ? 3 : 0);
			for (int i = start; i < dirLR.length - end; i++) {

				int n = step + dirLR[i];
				if (n >= 0 && n < size && passable[n] && distance[n] > nextDistance) {
					// Add to queue
					queue[tail++] = n;
					distance[n] = nextDistance;
				}

			}
		}

		return dist;
	}

	public static void buildDistanceMap( int to, boolean[] passable ) {

		System.arraycopy(maxVal, 0, distance, 0, maxVal.length);

		int head = 0;
		int tail = 0;

		// Add to queue
		queue[tail++] = to;
		distance[to] = 0;

		while (head < tail) {

			// Remove from queue
			int step = queue[head++];
			int nextDistance = distance[step] + 1;

			int start = (step % width == 0 ? 3 : 0);
			int end   = ((step+1) % width == 0 ? 3 : 0);
			for (int i = start; i < dirLR.length - end; i++) {

				int n = step + dirLR[i];
				if (n >= 0 && n < size && passable[n] && (distance[n] > nextDistance)) {
					// Add to queue
					queue[tail++] = n;
					distance[n] = nextDistance;
				}

			}
		}
	}

	@SuppressWarnings("serial")
	public static class Path extends LinkedList<Integer> {
	}
}
