import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileRecovery {
    public static void main(String[] args)
        {
			Scanner sc = new Scanner(System.in);
            System.out.println("Input: ");
            
            // Read the first cluster of each existing file in the root table
            String[] rootTable = sc.nextLine().split(" ");
            
            // Read the first row of the FAT table
            String[] fatRow1 = sc.nextLine().split(" ");
            
            // Read the second row of the FAT table
            String[] fatRow2 = sc.nextLine().split(" ");
            
            // Initialize a list to store the clusters of the deleted file
            List<String> deletedFileClusters = new ArrayList<>();
            
            // Start with the first cluster of the deleted file
            String currentCluster = rootTable[0];
            
            System.out.println();
            
            // Traverse the FAT table to find the associated clusters of the deleted file
            while (!currentCluster.equals("0")) {
               // Add a check to prevent infinite loops
               if (deletedFileClusters.contains(currentCluster)) {
                   //System.err.println("Error: Loop detected in FAT table.");
                   break;
               }

               deletedFileClusters.add(currentCluster);
               int row = Integer.parseInt(currentCluster) / fatRow1.length;
               int col = Integer.parseInt(currentCluster) % fatRow1.length; 
               
               // Check if row or col values are out of bounds
               if (row > 2 || col >= fatRow1.length) {
                   System.err.println("Error: Invalid cluster reference.");
                   break;
               }

               // Move to the next cluster based on the FAT table
               if (row == 0) {
                   currentCluster = fatRow1[col];
               } else {
                   currentCluster = fatRow2[col];
               }
            }
            
            System.out.println("Output: ");
            
            // Print the clusters of the deleted file in the correct sequence
            for (String cluster : deletedFileClusters) {
            	System.out.print(cluster + " ");
            }
        }
    }
