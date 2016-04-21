String url  = "http://jsonplaceholder.typicode.com/albums";
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,url,
                new Response.Listener<JSONArray>() {
                    String title;
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TAG", response.toString());
                        for(int i = 0; i < response.length(); ++i) {
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                array.add(jo.getString("title"));
                                title = jo.getString("title");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        long time=System.currentTimeMillis();
                        Toast.makeText(MainActivity.this, time+"start",Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,title,Toast.LENGTH_LONG).show();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });
        request.add(jsonObjReq);
        long time=System.currentTimeMillis();
        Toast.makeText(MainActivity.this, time+"end",Toast.LENGTH_LONG).show();
