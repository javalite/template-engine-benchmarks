/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import org.javalite.common.Util;
import org.javalite.templator.Template;
import teb.model.Stock;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplatorTest extends _BenchBase {

    Template template;
    public TemplatorTest() throws Exception {
        template = new Template(Util.readFile("templates/stocks.templator.html"));
    }

    @Override
    public void execute(Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        Map root = new HashMap();
        root.put("items", items);
        while (--ntimes >= 0) {
            if (ntimes == 0) {
                template.process(root,w1);
                w1.close();
            }
            else template.process(root, w0);
        }
    }

    @Override
    public void execute(OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        Map root = new HashMap();
        root.put("items", items);
        Writer w0 = new OutputStreamWriter(o0);
        Writer w1 = new OutputStreamWriter(o1);
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        while (--ntimes >= 0) {
            if (ntimes == 0) {
                template.process(root, w1);
                w1.close();
            }
            else template.process(root, w0);
        }
    }

    @Override
    protected String execute(int ntimes, List<Stock> items) throws Exception {
        Map root = new HashMap();
        root.put("items", items);
        Writer w0 = new StringWriter();
        Writer w1 = new StringWriter();
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        while (--ntimes >= 0) {
            if (ntimes == 0) {
                template.process(root, w1);
                w1.close();
            }
            else template.process(root, w0);
        }

        return w1.toString();
    }

    public static void main(String[] args) throws Exception {


        new TemplatorTest().run();
    }

}
