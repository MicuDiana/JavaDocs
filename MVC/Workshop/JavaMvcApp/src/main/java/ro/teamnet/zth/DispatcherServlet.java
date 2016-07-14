package ro.teamnet.zth;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.appl.controller.DepartmentController;
import ro.teamnet.zth.appl.controller.EmployeeController;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by user on 7/14/2016.
 */
public class DispatcherServlet extends HttpServlet {

    //key : urlPath
    //value: detalii despre metoda care se ocupa de procesarea acestui url
     HashMap<String, MethodAttributes> allowedMethods = new HashMap<String, MethodAttributes>();

    @Override
    public void init() throws ServletException {
        try {
            /*cautare clase din pachet*/
            Iterable<Class> controllers = AnnotationScanUtils.getClasses("ro.teamnet.zth.appl.controller");
            for(Class controller : controllers) {
                if(controller.isAnnotationPresent(MyController.class)) {
                    MyController myController = (MyController) controller.getAnnotation(MyController.class);
                    String controllerUrlPath = myController.urlPath();
                    Method[] controllerMethods = controller.getMethods();
                    for(Method controllerMethod : controllerMethods) {
                        if(controllerMethod.isAnnotationPresent(MyRequestMethod.class)) {
                            MyRequestMethod myRequestMethod = controllerMethod.getAnnotation(MyRequestMethod.class);
                            String methodUrlPath = myRequestMethod.urlPath();
                            //construiesc cheia
                            String urlPath = controllerUrlPath + methodUrlPath;

                            //construiesc valoarea
                            MethodAttributes methodAttributes = new MethodAttributes();
                            methodAttributes.setControllerClass(controller.getName());
                            methodAttributes.setMethodName(controllerMethod.getName());
                            methodAttributes.setMethodType(myRequestMethod.methodType());
                            allowedMethods.put(urlPath,methodAttributes);
                        }


                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispachReply("GET", req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispachReply("POST", req, resp);
    }

    protected void dispachReply(String type, HttpServletRequest req, HttpServletResponse resp ) {
        Object r = null;
        try {
             r = dispatch(req, resp);
        }catch (Exception ex) {
            sendExceptionError(ex, req, resp);
        }
        try {
            replay(r, req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendExceptionError(Exception ex, HttpServletRequest req, HttpServletResponse resp) {
    }


    private void replay(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.printf(r.toString());
    }

    private Object dispatch(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String pathInfo = req.getPathInfo();



         MethodAttributes methodAttributes = allowedMethods.get(pathInfo);

        if(methodAttributes == null)
            return "Hello"; //Nu putem procesa url.
        Object method  = Class.forName(methodAttributes.getControllerClass()).newInstance();
        return method.getClass().getMethod(methodAttributes.getMethodName()).invoke(method);

    }
}
