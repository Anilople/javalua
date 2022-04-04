-- page 202
-- 第10章 闭包和Upvalue
function newCounter()
    local count = 0
    return function () -- 匿名函数
        count = count + 1
        return count
    end
end

c1 = newCounter()
local value = c1()
print(value) --> 1