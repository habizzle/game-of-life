Testing
=======

cURL
----

Use
```
curl -X GET -H "Content-Type: application-json" 'http://localhost:4567/nextRound' -v -d '{
                                            "matchfield": {
                                                "size": {"width": 4, "height": 4},
                                                "alive": [
                                                    {"x": 1, "y": 2}
                                                ]
                                            }
                                        }'
```
