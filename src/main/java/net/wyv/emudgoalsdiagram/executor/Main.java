package net.wyv.emudgoalsdiagram.executor;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import net.wyv.emudgoalsdiagram.loaders.PRGLoader;
import net.wyv.emudgoalsdiagram.mappers.GoalsToDOT;
import net.wyv.emudgoalsdiagram.models.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class Main {

    @Autowired
    private PRGLoader loader;

    @PostConstruct
    public void init() {
        Map<Integer,Goal> goalMap = loader.load();
        String dotCode = (new GoalsToDOT().apply(goalMap));

        try {
            InputStream is = new ByteArrayInputStream( dotCode.getBytes("utf-8"));

            MutableGraph graph = Parser.read(is);

            Graphviz
                    .fromGraph(graph)
                    //.width(700)
                    .render(Format.PNG)
                    .toFile(new File("output/graph.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
