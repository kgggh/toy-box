# toy-box
장난간 박스안의 장난감처럼 언제든 꺼내서 가지고 놀만한 유틸성 모듈들을 모아둔 저장소

* Java11
* Junit5

## 개요
개발하다 보면 당장 필요하거나 필요성을 느낄만한 유틸이 있을 때를 대비해  
참고 || import해서 쓰려고 만들게 된 이유.

* fssPage 
  * Filter + Search + Sort + Pagination을 제공
  * Collection중 보편적인 List 기준

## Example
```java
USAGE CODE
    /* given */
    ArrayList<SearchCriteria> arr = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
        if(i % 2 == 0) {
            SearchCriteria searchCriteria = SearchCriteria.builder()
                    .name("my-name-"+i)
                    .description("umm....")
                    .isNext(true)
                    .num(i)
                    .build();
            arr.add(searchCriteria);
        } else {
            SearchCriteria searchCriteria = SearchCriteria.builder()
                    .name("my-name"+i)
                    .description("umm....")
                    .isNext(false)
                    .num(i)
                    .build();
            arr.add(searchCriteria);
        }
    }

    /* when */
    FssPageInfo criteria = FssPageInfo.builder()
            .filter("name", "my-name-2")
            .page(0)
            .size(10)
            .build();

    /* then */
    PageInfo<SearchCriteria> page = 
            FssPage.page(criteria, arr);

    /* result */
    PageInfo(data=[Usage.SearchCriteria(name=my-name-2, description=umm...., isNext=true, num=2)], totalCount=1, totalPage=1, page=0, size=10)
```
* jsonConverter
  * jsonObject to map 
  * map to json
