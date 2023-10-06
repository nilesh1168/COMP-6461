<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#usage">Installation / Usage</a></li>
      </ul>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
  </ol>
</details>

### About The Project
cURL like command implementation 

### Usage

1. Unzip the project into a folder say (Computer-Networks).
2. Goto Computer-Networks/bin
3. Open a terminal/command prompt at the above mentioned location.
4. In the terminal/ command prompt you can use the cURL command like utility (httpc). See the command <a href="#example-section">example section</a>.

**Note:** Make sure you create a file.json at Computer-Networks/bin to be able to test the -f option for post.

If you import the project into an IDE (Eclipse preferred) follow the below steps:
1. In Eclipse IDE select the Run option from the Menu Bar, followed by Run Configurations.
2. Under Java Application -> New Launch Configuration.
3. Select the project. Select the main class as "com.gcs.cn.httpc".
4. In the Arguments tab give the following arguemnts to test.
- help
- help post
- help get
- get 'http://httpbin.org/get?course=networking&assignment=1'
- get -v 'http://httpbin.org/get?course=networking&assignment=1'
- get  -v -o hello.txt 'http://httpbin.org/get?course=networking&assignment=1'
- get -v -h "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjk1ODY2MTQ4LCJpYXQiOjE2OTU4NjI1NDgsImp0aSI6ImM3ZDgxOTNkZTNlZjQ2MzdiM2U5MWIxMjVlMDgyMjY5IiwidXNlcl9pZCI6MSwidXNlcm5hbWUiOiJuaWxlc2gifQ.5VIo3RPmISIHT_Bs-w0C84YhdYtDdfCY5CashXigDeY" "http://httpbin.org/get?course=networking&assignment=1"
- post -h Content-Type:application/json -d '{"Assignment": 1}' http://httpbin.org/post
- post -h Content-Type:application/json -d '{"Assignment": 1}' http://httpbin.org/post -o post.txt
- post -h Content-Type:application/json -f file.json http://httpbin.org/post
5. Next, Apply -> Run.

**Note:** Make sure you create a file.json at top level in the IDE. For example

|- bin

|- src

|- **file.json**

|- README.md
## Example Section

- java com.gcs.cn.httpc help
- java com.gcs.cn.httpc help post
- java com.gcs.cn.httpc help get
- java com.gcs.cn.httpc get 'http://httpbin.org/get?course=networking&assignment=1'
- java com.gcs.cn.httpc get -v 'http://httpbin.org/get?course=networking&assignment=1'
- java com.gcs.cn.httpc get  -v -o hello.txt 'http://httpbin.org/get?course=networking&assignment=1'
- java com.gcs.cn.httpc get -v -h "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjk1ODY2MTQ4LCJpYXQiOjE2OTU4NjI1NDgsImp0aSI6ImM3ZDgxOTNkZTNlZjQ2MzdiM2U5MWIxMjVlMDgyMjY5IiwidXNlcl9pZCI6MSwidXNlcm5hbWUiOiJuaWxlc2gifQ.5VIo3RPmISIHT_Bs-w0C84YhdYtDdfCY5CashXigDeY" "http://httpbin.org/get?course=networking&assignment=1"
- java com.gcs.cn.httpc post -h Content-Type:application/json -d '{"Assignment": 1}' http://httpbin.org/post
- java com.gcs.cn.httpc post -h Content-Type:application/json -d '{"Assignment": 1}' http://httpbin.org/post -o post.txt
- java com.gcs.cn.httpc post -h Content-Type:application/json -f file.json http://httpbin.org/post

### Built With

* ![Java](https://img.shields.io/badge/java-red?style=for-the-badge&logo=Java&logoColor=red)


