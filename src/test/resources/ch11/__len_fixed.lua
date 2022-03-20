-- 元方法 __len
mt = {}
-- 总是返回固定长度
mt.__len = function(t)
    return 999
end

t = {}
setmetatable(t, mt)
print(#t) -- 999