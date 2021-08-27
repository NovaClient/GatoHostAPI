# This is the archived version! Gato.Host is making it the official Java API, and moved it to their organization!
# New Repository: https://github.com/Gato-Host/GatoHostAPI-Java
# Official GatoHost API 【Public Version】
A java API that allows you to get/set user info and more with java code and user's key!
## API Info
Supported Official API Version: `v2`<br>
Default Base URL: `https://gato.host`<br>Java Version: `1.8+`<br>Latest Java API Version: `2.0-BETA`<br>

## Usage

Please check `rootdir/src/test/java` for all usages.

### About Example Usages

If you want to use them, please add an user-info.json file to your run folder. It should be something like this:

```json
{
    "key": "YOUR_KEY_THAT_GATOHOST_GIVES_YOU_IN_PANEL",
    "baseURL": "https://gato.host"
}
```

Well, you won't need that if you are not running our examples.

### If you are lazy

Here's a usage to set user info. Other requests should look same.

```java
GatoHostAPI api = new GatoHostAPI("https://gato.host", "KEY_HERE"); // Create new GatoAPI instance.
GatoHostAPI.SetUserInfoQuery query = new GatoHostAPI.SetUserInfoQuery.Builder(api)
                .authorText("WORKED OMG!")
                .build(); // Build a request with Builder. Some requests that doesn't require that much argument doesn't have builder.
GatoHostAPI.SetUserInfoResponse execute = api.execute(query); // Execute a request and get the response. There's nothing in SetUserInfo Response. But there should be getters in GetUserInfoResponse.
```



## License

We are using [WTFPL](http://www.wtfpl.net/). It means you can do anything you want to the API.

```
        DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
                    Version 2, December 2004 

 Copyright (C) 2004 Sam Hocevar <sam@hocevar.net> 

 Everyone is permitted to copy and distribute verbatim or modified 
 copies of this license document, and changing it is allowed as long 
 as the name is changed. 

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 

  0. You just DO WHAT THE FUCK YOU WANT TO.
```

## Archived
This repository is archived and there won't be update anymore. If you want updates, go for gatohost official forked one: https://github.com/Gato-Host/GatoHostAPI-Java<br>
