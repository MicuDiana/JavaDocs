package ro.teamnet.zth;

import org.codehaus.jackson.map.ObjectMapper;
import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                            methodAttributes.setParameterType(controllerMethod.getParameterTypes());
                            allowedMethods.put(urlPath + myRequestMethod.methodType(),methodAttributes);
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
        dispachReply(req, resp, "GET");


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispachReply(req, resp, "DELETE");
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispachReply(req, resp, "POST");
    }

    protected void dispachReply(HttpServletRequest req, HttpServletResponse resp, String methodType ) {
        Object r = null;
        try {
             r = dispatch(req, resp);
        }catch (Exception ex) {
            sendExceptionError(ex, req, resp);
        }
        try {
            reply(r, req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendExceptionError(Exception ex, HttpServletRequest req, HttpServletResponse resp) {
    }


    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper myObjectMapper = new ObjectMapper();
        String jsonString = myObjectMapper.writeValueAsString(r);

        PrintWriter out = resp.getWriter();
        out.printf(jsonString);
    }

    private Object dispatch(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException {

        String pathInfo = req.getPathInfo();
        pathInfo += req.getMethod();

        List<Object> parameterValue = new ArrayList<>();
         MethodAttributes methodAttributes = allowedMethods.get(pathInfo);

       // pathInfo = pathInfo + methodAttributes.getMethodType();
        if(methodAttributes == null)
            return "Hello"; //Nu putem procesa url.

        Class<?> controllerClass = Class.forName(methodAttributes.getControllerClass());
        Object controller = controllerClass.newInstance();
        Parameter[] parameters = controllerClass.getMethod(methodAttributes.getMethodName(),
                methodAttributes.getParameterType()).getParameters();

        for(Parameter parameter: parameters) {
            if(parameter.isAnnotationPresent(MyRequestParam.class)){
                MyRequestParam annotation = parameter.getAnnotation(MyRequestParam.class);
                String name = annotation.name();
                String requestParamValue = req.getParameter(name);
                Class<?> type = parameter.getType();


                Object requestParamObject;

                if(parameter.getType().equals(String.class)){
                    requestParamObject = requestParamValue;
                }
                else{
                    requestParamObject = new ObjectMapper().readValue(requestParamValue, type);
                }
                parameterValue.add(requestParamObject);



            }

        }

        Method method = controllerClass.getMethod(methodAttributes.getMethodName(), methodAttributes.getParameterType());
        return method.invoke(controller, parameterValue.toArray());

    }
}
