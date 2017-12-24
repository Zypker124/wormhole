
/*
ID: angusjy1
LANG: JAVA
TASK: wormhole
*/
import java.io.*;
import java.util.*;

class wormhole {
	
	static int maxNum = 12;
	static int numOfWormholes = 0;
	static int [] x = new int[maxNum + 1];
	static int [] y = new int[maxNum + 1];
	static int [] partners = new int[maxNum + 1]; // Array with a number j at the ith index, meaning j is i's partner
	static int [] neighbor_right = new int[maxNum + 1];
  public static void main (String [] args) throws IOException {
    // Use BufferedReader rather than RandomAccessFile; it's much faster
    BufferedReader reader = new BufferedReader(new FileReader("wormhole.in"));
                                                  // input file name goes above
    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));
    // Use StringTokenizer vs. readLine/split -- lots faster
    StringTokenizer st = new StringTokenizer(reader.readLine());
						  // Get line, break into tokens
    numOfWormholes = Integer.parseInt(st.nextToken());
    for(int ind = 1; ind <= numOfWormholes; ind++){
    	st = new StringTokenizer(reader.readLine());
    	x[ind] = Integer.parseInt(st.nextToken());
    	y[ind] = Integer.parseInt(st.nextToken());
    }
    for(int i = 1; i <= numOfWormholes; i++) {
		for(int j = 1; j <= numOfWormholes; j++) {
			if(x[j] > x[i] && y[i] == y[j]) {
				if(neighbor_right[i] == 0 || x[j] - x[i] < x[neighbor_right[i]] - x[i]) {
					neighbor_right[i] = j;
				}
			}
		}
	}
    writer.println(solve());
    writer.close();                                  // close the output file
  }
  
  public static int solve(){
	  int total = 0;
	  //First find i such that i does not have a partner
	  int i = 0;
	  for(i = 1; i <= numOfWormholes; i++){
		  if(partners[i]==0){
			  break;
		  }
	  }
	  if(i > numOfWormholes){
		  if(isACycle()){
			  return 1;
		  }else{
			  return 0;
		  }
	  }
	  for(int j = i + 1; j <= numOfWormholes; j++){
		  if(partners[j]==0){
			  //Now we try to partner i with j
			  partners[i] = j;
			  partners[j] = i;
			  total += solve();
			  partners[i] = partners[j] = 0;
		  }
	  }
	  return total;
  }
  
  public static boolean isACycle(){
	  for(int start = 1; start <= numOfWormholes; start++){
		  int position = start;
		  for(int count = 0; count < numOfWormholes; count++){
			  position = neighbor_right[partners[position]];
		  }
		  if(position!=0){
			  return true;
		  }
	  }
	  return false;
  }
}