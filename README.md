# SD-Compare

SD-Compare is a elasticsearch plugin that exposes a _compare endpoint that functions exactly like the _search endpoint expect it gives you a comparison object with a field level breakdown of the scoring:

```json
{
   "comparison":[
      {
         "id":"3",
         "scores":{
            "line_id":0.97265494,
            "speech_number":1.2
         }
      },
      {
         "id":"4",
         "scores":{
            "line_id":0.9576033,
            "speech_number":1.2
         }
      },
      {
         "id":"8",
         "scores":{
            "line_id":0.86904335,
            "speech_number":1.2
         }
      },
      {
         "id":"9",
         "scores":{
            "line_id":0.8408964,
            "speech_number":1.2
         }
      },
      {
         "id":"10",
         "scores":{
            "line_id":0.8108461,
            "speech_number":1.2
         }
      }
   ]
}
```


###Pitfalls
At the moment you need to make sure that you have `explain: true` included in your query.

This is the first ES plugin i've created so feel free to offer suggestions for improvement

### Todo's

 - Remove need to have `explain: true` in the query

License
----

MIT
