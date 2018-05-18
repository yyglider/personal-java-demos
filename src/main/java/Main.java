import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {


//        Stream<List<Integer>> stream2 = Stream.of(Arrays.asList(1,2,3), Arrays.asList(3, 4, 5,6));
//
//        stream2.collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
//               .forEach((integers, aLong) -> System.out.println(integers + ":" + aLong));



        Stream<List<Integer>> stream = Stream.of(Arrays.asList(1,2,3,4), Arrays.asList(3,3, 4, 5,6));





//                .forEach((integers, aLong) -> System.out.println(integers + ":" + aLong));


        //        String file = "acbauc@$#sa..adfaAWr.3e.adf.jpg";
//        System.out.println(file.substring(file.lastIndexOf(".")));

        /*JSONObject must1 = createContentQueryJSON("刘其普提问下");
        JSONObject must2 = createGroupQueryJSON(Arrays.asList(281));

        JSONObject must = new JSONObject();
        JSONArray mustArray = new JSONArray();
        mustArray.add(must1);
        mustArray.add(must2);
        must.put("must",mustArray);

        JSONObject bool = new JSONObject();
        bool.put("bool",must);


        JSONObject result = new JSONObject();
        result.put("from",0);
        result.put("size",8);
        result.put("query",bool);



        System.out.println(result);*/
    }

    // 创建小组搜索内容的JSONObject对象
    private static JSONObject createContentQueryJSON(String queryContent) {

        JSONObject demandTitle = new JSONObject();
        demandTitle.put("demandTitle", queryContent);

        JSONObject detail = new JSONObject();
        detail.put("detail", queryContent);

        JSONObject match1 = new JSONObject();
        match1.put("match",demandTitle);


        JSONObject match2 = new JSONObject();
        match2.put("match",detail);

        JSONObject should = new JSONObject();
        JSONArray shouldArray = new JSONArray();
        shouldArray.add(match1);
        shouldArray.add(match2);
        should.put("should",shouldArray);

        JSONObject bool = new JSONObject();
        bool.put("bool",should);

        return bool;
    }

    // 创建搜索小组的JSONObject对象
    private static JSONObject createGroupQueryJSON(List<Integer> groupIdList) {

        JSONObject should = new JSONObject();
        JSONArray shouldArray = new JSONArray();
        for(Integer groupId : groupIdList){
            JSONObject groupIdJosnObj = new JSONObject();
            groupIdJosnObj.put("groupId",groupId);

            JSONObject match = new JSONObject();
            match.put("match",groupIdJosnObj);
            shouldArray.add(match);

        }
        should.put("should",shouldArray);

        JSONObject bool = new JSONObject();
        bool.put("bool",should);

        return bool;
    }


}