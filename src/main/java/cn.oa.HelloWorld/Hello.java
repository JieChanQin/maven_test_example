package cn.oa.HelloWorld;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Hello {

    public static void main(String []args)  throws MalformedURLException {
        //api访问的url是固定格式，前面host部分换成你自己的就可以
        //devKey就是第一篇文章介绍的用户下点击new一个access token
        String testlink_Url = "http://localhost/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
        String devKey = "18a6726495d51556730fe08ab1bdd736";

        //将字符串的url转换成URL对象
        URL testlinkURL = new URL(testlink_Url);
        //调用TestLinkAPI构造方法，这两个参数就是上面的url和devKey
        //api是一个TestLinkAPI对象，这个对象是我们操作testlink的核心部分
        //接下来用到的所有API都是这个api发起调用的
        TestLinkAPI api = new TestLinkAPI(testlinkURL,devKey);
        System.out.println(api.ping());

        System.out.println("testProject:");
        //TestProject[] projects=api.getProjects();
        TestProject projects=api.getTestProjectByName("test_for_testlink");
        int projectId=projects.getId();
        /*for(TestProject testProject:projects){
            //System.out.println(projects[i].getName()+"  ..id"+projects[i].getId());
            if("test_for_testlink".equals(testProject.getName())){
                projectId=testProject.getId();
            }
        }

*/
        TestSuite[] suites = api.getFirstLevelTestSuitesForTestProject(projectId);
        TestCase[] cases = null;
        for (TestSuite testSuite : suites) {
            //获取测试用例数组,注意参数写法
            cases = api.getTestCasesForTestSuite(testSuite.getId(), true, null);
        }
        for(TestCase acase:cases) {
            System.out.println("测试套件中的TestCase:" + acase.getName());
        }
        for (TestSuite testSuite : suites) {
            System.out.println("测试套件"+testSuite.getName()+"有以下测试用例:");
            TestCase[] testCases=api.getTestCasesForTestSuite(testSuite.getId(),true,null);
            for(TestCase testCase:testCases){
                System.out.println(testCase.getName()+" 期待结果：");
                List<TestCaseStep> testCaseStep=testCase.getSteps();
                for(TestCaseStep caseStep:testCaseStep){
                    System.out.println("1"+caseStep.getExpectedResults());
                }

            }
        }


        TestPlan[] testPlan=api.getProjectTestPlans(projectId);
        for(TestPlan plan:testPlan){
            System.out.println(plan.getName()+"  planId?"+plan.getId()+
                    " projectName:"+plan.getProjectName());
        }


    }

}