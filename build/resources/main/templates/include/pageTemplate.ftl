[#ftl]
[#escape x as x?html]
[#macro pageTemplate title]
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
</head>
<body class="no-js">
    [#nested]
</body>
</html>
[/#macro]
[/#escape]

