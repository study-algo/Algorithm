import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// BOJ 17406
// Gold 4
// 구현
public class Main {
    static List<Integer[]> rotates = new ArrayList<>();
    static int[][] arr, tmp;
    static int answer = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        arr = new int[n][m];
        tmp = new int[n][m];
        for (int r=0; r<n; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c=0; c<m; c++) {
                arr[r][c] = Integer.parseInt(st.nextToken());
                tmp[r][c] = arr[r][c];
            }
        }
        for (int i=0; i<k; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            rotates.add(new Integer[]{r,c,s});
        }
        per(new int[k],k,new int[k],0);

        System.out.println(answer);

    }

    private static int getMin() {
        int min = Integer.MAX_VALUE;
        for (int r = 0; r< arr.length; r++) {
            int sum = 0;
            for (int c = 0; c<arr[0].length; c++) {
                sum+=arr[r][c];
            }
            min = Math.min(sum, min);
        }
        return min;
    }

    public static void rotate(int sy, int sx, int ey, int ex) {
        if (ey==sy && ex==sx) return;
        int[] dy = new int[]{0,1,0,-1};
        int[] dx = new int[]{1,0,-1,0};
        int idx = 0;
        int y = sy;
        int x = sx;
        int ptmp = arr[y][x];
        int tmp;
        while(true) {
            if (y+dy[idx]>ey || y+dy[idx]<sy || x+dx[idx]>ex || x+dx[idx]<sx) idx++;
            if (idx == 4) break;
            y = y+dy[idx];
            x = x+dx[idx];
            tmp = arr[y][x];
            arr[y][x] = ptmp;
            ptmp = tmp;
        }
        rotate(sy+1,sx+1,ey-1,ex-1);
    }

    public static void per(int[] output, int k, int[] visit, int depth) {
        if (depth == k) {
            for (int i=0; i<k; i++) {
                Integer[] rs = rotates.get(output[i]);
                int r = rs[0];
                int c = rs[1];
                int s = rs[2];
                rotate(r-s-1,c-s-1,r+s-1,c+s-1);
            }
            answer = Math.min(getMin(), answer);

            for (int r=0; r<arr.length; r++) {
                for (int c=0; c<arr[0].length; c++) {
                    arr[r][c] = tmp[r][c];
                }
            }

            return;
        }
        for (int i=0; i<k; i++) {
            if (visit[i] == 0) {
                visit[i] = 1;
                output[depth] = i;
                per(output,k,visit,depth+1);
                visit[i]=0;
            }
        }
    }
}
