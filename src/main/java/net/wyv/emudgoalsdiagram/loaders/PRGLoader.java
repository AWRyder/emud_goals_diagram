package net.wyv.emudgoalsdiagram.loaders;

import net.wyv.emudgoalsdiagram.models.Goal;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PRGLoader {

    private static final String PRG_FOLDER = "prg";


    public Map<Integer,Goal> load() {

        Map<Integer,Goal> goals = new HashMap<>();

        File[] prgFiles = getResourceFolderFiles(PRG_FOLDER);

        for (File prgFile : prgFiles) {

            try (BufferedReader br = Files.newBufferedReader(prgFile.toPath())) {

                List<String> lines = br.lines().collect(Collectors.toList());
                Goal goal = null;
                String[] lineSplt = new String[]{};
                boolean doneWithPoints = false;
                for (String line : lines) {
                    if (line.startsWith("#")) {
                        Integer id = Integer.parseInt(line.substring(1));
                        if (goal != null) {
                            goals.put(goal.getId(),goal);
                        }
                        goal = new Goal();
                        goal.setId(id);
                        doneWithPoints=false;
                    } else if (line.endsWith("~") && !line.startsWith("~")) {
                        goal.setName(line.replace("~", ""));
                    } else if (line.startsWith("P ")){
                        Integer dependency = Integer.parseInt(line.substring(2));
                        goal.getDependencies().add(dependency);
                    } else if (line.matches("\\d\\s\\d+\\s\\d+\\s\\d+.*") && !doneWithPoints) {
                        lineSplt = line.split(" ");
                    } else if ( line.startsWith ("W") && !doneWithPoints ){
                        goal.setReward(false);
                        goal.setPoints(Integer.parseInt(lineSplt[3]));
                        doneWithPoints=true;
                    } else if ( line.startsWith ("S") && lineSplt.length >= 3 ){
                        if ( goal.getReward() ){
                            goal.setRewardCost(Integer.parseInt(lineSplt[2]));
                        }
                    }
                }
                if (goal != null) {
                    goals.put(goal.getId(),goal);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /*goals.forEach(
                (k,g) -> {
                    System.out.println(g.getId() + ": " + g.getName() + " --- "+g.getPoints());
                    g.getDependencies().forEach( depId -> System.out.println(" -> "+depId) );
                }
        );

        System.out.println("Total: "+goals.keySet().size());*/

        return goals;
    }


    private static File[] getResourceFolderFiles(String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        String path = url.getPath();
        return new File(path).listFiles();
    }

}
