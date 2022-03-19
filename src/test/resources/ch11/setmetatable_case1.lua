t = {}; mt = {x=999}
setmetatable(t, mt)
local mt_of_t = getmetatable(t)
print(mt_of_t['x']) -- 999