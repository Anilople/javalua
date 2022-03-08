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
print(c1()) --> 1
print(c1()) --> 2

c2 = newCounter()
print(c2()) --> 1
print(c1()) --> 3
print(c3()) --> 2