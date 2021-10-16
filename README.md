```
POST /attachments/upload HTTP/1.1
Host: localhost:8080
Content-Length: 187
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="SampleDOC.doc"
Content-Type: application/msword

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW
```

`SampleDOCX.docx` -> `application/x-tika-ooxml`
`failingSampleCreatedByLibreOffice.docx` -> `application/x-tika-ooxml`
`workingSampleFromRealOffice` -> `application/vnd.openxmlformats-officedocument.wordprocessingml.document`
